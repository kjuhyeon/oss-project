import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DeviceMonitorApp extends JFrame {
    private static final DateTimeFormatter TABLE_TIME = DateTimeFormatter.ofPattern("MM-dd HH:mm");
    private static final DateTimeFormatter HISTORY_TIME = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");

    private final List<Device> devices = new ArrayList<>();
    private final List<HistoryRecord> historyRecords = new ArrayList<>();
    private final Random random = new Random();

    private final DeviceTableModel deviceTableModel = new DeviceTableModel();
    private final HistoryTableModel historyTableModel = new HistoryTableModel();

    private final JTextField deviceIdField = new JTextField();
    private final JTextField deviceNameField = new JTextField();
    private final JTextField deviceLocationField = new JTextField();
    private final JComboBox<Device> selectedDeviceComboBox = new JComboBox<>();
    private final JSpinner temperatureSpinner = new JSpinner(new SpinnerNumberModel(36.0, 0.0, 120.0, 0.5));
    private final JSpinner vibrationSpinner = new JSpinner(new SpinnerNumberModel(2.0, 0.0, 20.0, 0.1));
    private final JSpinner powerSpinner = new JSpinner(new SpinnerNumberModel(90, 0, 100, 1));
    private final JTextField memoField = new JTextField();
    private final JTextArea alertArea = new JTextArea();
    private final JLabel alertCheckedLabel = new JLabel("알림 확인 전");

    private final JLabel totalCountLabel = new JLabel("0");
    private final JLabel normalCountLabel = new JLabel("0");
    private final JLabel warningOrAbnormalCountLabel = new JLabel("0");
    private final JLabel historyCountLabel = new JLabel("0");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setNimbusLookAndFeel();
                new DeviceMonitorApp().setVisible(true);
            }
        });
    }

    private static void setNimbusLookAndFeel() {
        try {
            UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
            for (UIManager.LookAndFeelInfo info : infos) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (Exception ignored) {
            // Use the default Swing look and feel when Nimbus is unavailable.
        }
    }

    public DeviceMonitorApp() {
        super("Device Status Monitoring and Inspection Support System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 720));
        setSize(new Dimension(1320, 780));
        setLocationRelativeTo(null);

        createSampleData();
        setContentPane(createContentPane());
        updateDashboard();
    }

    private JPanel createContentPane() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Colors.BACKGROUND);
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        return root;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(18, 0));
        header.setBorder(new EmptyBorder(14, 28, 14, 28));
        header.setBackground(Colors.NAVY);

        JLabel title = new JLabel("Device Status Monitoring and Inspection Support System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Malgun Gothic", Font.BOLD, 23));

        JLabel subtitle = new JLabel("장비 등록 · 상태 입력 · 이상 알림 · 점검 기록 · 이력/통계 조회");
        subtitle.setForeground(new Color(216, 226, 239));
        subtitle.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);

        header.add(textPanel, BorderLayout.WEST);
        return header;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(18, 0));
        main.setBorder(new EmptyBorder(12, 18, 14, 18));
        main.setBackground(Colors.BACKGROUND);
        main.add(createLeftPanel(), BorderLayout.WEST);
        main.add(createRightPanel(), BorderLayout.CENTER);
        return main;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(380, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        leftPanel.add(createSection("Register device", createRegisterPanel()));
        leftPanel.add(Box.createVerticalStrut(14));
        leftPanel.add(createSection("Input status / Inspection", createStatusPanel()));

        return leftPanel;
    }

    private JPanel createRegisterPanel() {
        JButton registerButton = createPrimaryButton("장비 등록");
        registerButton.addActionListener(event -> registerDevice());

        JPanel panel = createVerticalPanel();
        panel.add(createField("장비 ID", deviceIdField));
        panel.add(createField("장비명", deviceNameField));
        panel.add(createField("위치", deviceLocationField));
        panel.add(registerButton);
        return panel;
    }

    private JPanel createStatusPanel() {
        JButton saveStatusButton = createPrimaryButton("상태 저장");
        JButton inspectButton = createSecondaryButton("점검 수행");
        JButton receiveStatusButton = createSecondaryButton("가상 상태 수신");

        saveStatusButton.addActionListener(event -> saveStatusData());
        inspectButton.addActionListener(event -> executeInspection());
        receiveStatusButton.addActionListener(event -> receiveStatusData());

        JPanel panel = createVerticalPanel();
        panel.add(createField("대상 장비", selectedDeviceComboBox));
        panel.add(createField("온도(°C)", temperatureSpinner));
        panel.add(createField("진동", vibrationSpinner));
        panel.add(createField("전원(%)", powerSpinner));
        panel.add(createField("메모", memoField));

        JPanel buttonRow = new JPanel(new GridLayout(1, 3, 8, 0));
        buttonRow.setOpaque(false);
        buttonRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        buttonRow.add(saveStatusButton);
        buttonRow.add(inspectButton);
        buttonRow.add(receiveStatusButton);
        panel.add(buttonRow);
        return panel;
    }

    private JPanel createAlertPanel() {
        alertArea.setEditable(false);
        alertArea.setLineWrap(true);
        alertArea.setWrapStyleWord(true);
        alertArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        alertArea.setForeground(Colors.TEXT);
        alertArea.setBackground(new Color(255, 250, 240));
        alertArea.setBorder(new EmptyBorder(8, 10, 8, 10));

        JScrollPane alertScroll = new JScrollPane(alertArea);
        alertScroll.setPreferredSize(new Dimension(280, 132));
        alertScroll.setBorder(BorderFactory.createLineBorder(new Color(238, 214, 165)));

        JButton checkButton = createSecondaryButton("알림 확인");
        checkButton.addActionListener(event -> {
            checkAlertNotification();
            alertCheckedLabel.setText("마지막 확인: " + LocalDateTime.now().format(TABLE_TIME));
        });

        alertCheckedLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        alertCheckedLabel.setForeground(Colors.MUTED);

        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        panel.add(alertScroll, BorderLayout.CENTER);
        panel.add(checkButton, BorderLayout.SOUTH);
        panel.add(alertCheckedLabel, BorderLayout.NORTH);
        return panel;
    }

    private JTabbedPane createRightPanel() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
        tabs.addTab("Dashboard", createDashboardPanel());
        tabs.addTab("History view", createHistoryPanel());
        return tabs;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Colors.BACKGROUND);
        panel.add(createStatisticsPanel(), BorderLayout.NORTH);

        JPanel dashboardBody = new JPanel(new BorderLayout(0, 10));
        dashboardBody.setOpaque(false);
        dashboardBody.add(createSection("Alert notification", createAlertPanel()), BorderLayout.NORTH);
        dashboardBody.add(createSection("Query device list", createDeviceTable()), BorderLayout.CENTER);

        panel.add(dashboardBody, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 14, 0));
        panel.setOpaque(false);
        panel.add(createStatCard("등록 장비", totalCountLabel, Colors.BLUE));
        panel.add(createStatCard("정상 장비", normalCountLabel, Colors.GREEN));
        panel.add(createStatCard("주의/이상", warningOrAbnormalCountLabel, Colors.RED));
        panel.add(createStatCard("누적 기록", historyCountLabel, Colors.TEAL));
        return panel;
    }

    private JScrollPane createDeviceTable() {
        JTable table = new JTable(deviceTableModel);
        configureTable(table);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(6).setCellRenderer(new StatusRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Colors.BORDER));
        return scrollPane;
    }

    private JPanel createHistoryPanel() {
        JTable table = new JTable(historyTableModel);
        configureTable(table);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(7).setCellRenderer(new StatusRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(105);
        table.getColumnModel().getColumn(1).setPreferredWidth(65);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(8).setPreferredWidth(210);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Colors.BORDER));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.BACKGROUND);
        panel.setBorder(new EmptyBorder(16, 0, 0, 0));
        panel.add(createSection("Query history data", scrollPane), BorderLayout.CENTER);
        return panel;
    }

    private void registerDevice() {
        String deviceId = deviceIdField.getText().trim();
        String name = deviceNameField.getText().trim();
        String location = deviceLocationField.getText().trim();

        if (deviceId.isEmpty() || name.isEmpty() || location.isEmpty()) {
            showMessage("장비 ID, 장비명, 위치를 모두 입력하세요.");
            return;
        }
        if (findDeviceById(deviceId) != null) {
            showMessage("이미 등록된 장비 ID입니다.");
            return;
        }

        Device device = new Device(deviceId, name, location);
        devices.add(device);

        deviceIdField.setText("");
        deviceNameField.setText("");
        deviceLocationField.setText("");
        updateDashboard();
    }

    private void saveStatusData() {
        Device device = getSelectedDevice();
        if (device == null) {
            showMessage("상태 데이터를 입력할 장비를 먼저 선택하세요.");
            return;
        }

        applyStatusData(
                device,
                getSpinnerValue(temperatureSpinner),
                getSpinnerValue(vibrationSpinner),
                getPowerPercentValue(),
                getMemoOrDefault("수동 상태 데이터 입력")
        );
        memoField.setText("");
        updateDashboard();
    }

    private void receiveStatusData() {
        if (devices.isEmpty()) {
            showMessage("가상 상태 데이터를 수신할 장비가 없습니다.");
            return;
        }

        for (Device device : devices) {
            double temperature = 28.0 + random.nextDouble() * 58.0;
            double vibration = 0.5 + random.nextDouble() * 9.5;
            int powerPercent = 52 + random.nextInt(49);
            applyStatusData(device, temperature, vibration, powerPercent, "Device 가상 상태 수신");
        }
        updateDashboard();
    }

    private void executeInspection() {
        Device device = getSelectedDevice();
        if (device == null) {
            showMessage("점검 대상 장비를 먼저 선택하세요.");
            return;
        }

        String defaultMemo;
        if (device.status == DeviceStatus.NORMAL) {
            defaultMemo = "점검 완료: 이상 없음";
        } else if (device.status == DeviceStatus.WARNING) {
            defaultMemo = "점검 필요: 주의 상태 확인";
        } else {
            defaultMemo = "긴급 점검 필요: 이상 상태 확인";
        }

        addHistoryRecord(new HistoryRecord(
                LocalDateTime.now(),
                RecordType.INSPECTION,
                device.deviceId,
                device.name,
                device.temperature,
                device.vibration,
                device.powerPercent,
                device.status,
                getMemoOrDefault(defaultMemo)
        ));
        memoField.setText("");
        updateDashboard();
    }

    private void checkAlertNotification() {
        StringBuilder builder = new StringBuilder();
        for (Device device : devices) {
            if (device.isWarningOrAbnormal()) {
                builder.append("[")
                        .append(device.getDisplayStatus())
                        .append("] ")
                        .append(device.name)
                        .append(" / ")
                        .append(device.location)
                        .append("\n")
                        .append(String.format(
                                Locale.US,
                                "온도 %.1f°C, 진동 %.1f, 전원 %d%%",
                                device.temperature,
                                device.vibration,
                                device.powerPercent
                        ))
                        .append("\n\n");
            }
        }

        if (builder.length() == 0) {
            builder.append("현재 표시할 알림이 없습니다.");
        }

        alertArea.setText(builder.toString().trim());
        alertArea.setCaretPosition(0);
    }

    private void queryDeviceList() {
        deviceTableModel.refresh();
    }

    private void queryHistoryData() {
        historyTableModel.refresh();
    }

    private void queryStatisticsData() {
        int normalCount = 0;
        int warningOrAbnormalCount = 0;

        for (Device device : devices) {
            if (device.status == DeviceStatus.NORMAL) {
                normalCount++;
            } else {
                warningOrAbnormalCount++;
            }
        }

        totalCountLabel.setText(String.valueOf(devices.size()));
        normalCountLabel.setText(String.valueOf(normalCount));
        warningOrAbnormalCountLabel.setText(String.valueOf(warningOrAbnormalCount));
        historyCountLabel.setText(String.valueOf(historyRecords.size()));
    }

    private DeviceStatus evaluateStatus(double temperature, double vibration, int powerPercent) {
        if (temperature >= 80.0 || vibration >= 8.0 || powerPercent <= 60.0) {
            return DeviceStatus.ABNORMAL;
        }
        if (temperature >= 65.0 || vibration >= 5.0 || powerPercent <= 75.0) {
            return DeviceStatus.WARNING;
        }
        return DeviceStatus.NORMAL;
    }

    private void updateDashboard() {
        refreshDeviceComboBox();
        queryDeviceList();
        queryHistoryData();
        queryStatisticsData();
        checkAlertNotification();
    }

    private void applyStatusData(Device device, double temperature, double vibration, int powerPercent, String memo) {
        DeviceStatus status = evaluateStatus(temperature, vibration, powerPercent);
        LocalDateTime now = LocalDateTime.now();
        device.updateStatusData(temperature, vibration, powerPercent, status, now);

        addHistoryRecord(new HistoryRecord(
                now,
                RecordType.STATUS,
                device.deviceId,
                device.name,
                temperature,
                vibration,
                powerPercent,
                status,
                memo
        ));
    }

    private void addHistoryRecord(HistoryRecord record) {
        historyRecords.add(0, record);
    }

    private void refreshDeviceComboBox() {
        Device selected = getSelectedDevice();
        selectedDeviceComboBox.removeAllItems();
        for (Device device : devices) {
            selectedDeviceComboBox.addItem(device);
        }

        if (selected != null) {
            for (Device device : devices) {
                if (device.deviceId.equals(selected.deviceId)) {
                    selectedDeviceComboBox.setSelectedItem(device);
                    return;
                }
            }
        }

        if (!devices.isEmpty()) {
            selectedDeviceComboBox.setSelectedIndex(0);
        }
    }

    private void createSampleData() {
        Device pump = new Device("D-001", "냉각 펌프", "1공장 A라인");
        Device fan = new Device("D-002", "배기 팬", "1공장 B라인");
        Device conveyor = new Device("D-003", "컨베이어", "물류 구역");
        Device compressor = new Device("D-004", "공기 압축기", "설비실");

        devices.add(pump);
        devices.add(fan);
        devices.add(conveyor);
        devices.add(compressor);

        applyStatusData(pump, 42.0, 2.3, 92, "정상 운전 상태");
        applyStatusData(fan, 69.0, 4.7, 78, "온도 상승 추적 필요");
        applyStatusData(conveyor, 54.0, 8.5, 70, "진동 수치 높음");
        applyStatusData(compressor, 38.0, 1.5, 88, "정상 운전 상태");
        addHistoryRecord(new HistoryRecord(
                LocalDateTime.now(),
                RecordType.INSPECTION,
                conveyor.deviceId,
                conveyor.name,
                conveyor.temperature,
                conveyor.vibration,
                conveyor.powerPercent,
                conveyor.status,
                "점검 기록: 진동 수치 확인 필요"
        ));
    }

    private Device getSelectedDevice() {
        Object selected = selectedDeviceComboBox.getSelectedItem();
        if (selected instanceof Device) {
            return (Device) selected;
        }
        return null;
    }

    private Device findDeviceById(String deviceId) {
        for (Device device : devices) {
            if (device.deviceId.equalsIgnoreCase(deviceId)) {
                return device;
            }
        }
        return null;
    }

    private double getSpinnerValue(JSpinner spinner) {
        return ((Number) spinner.getValue()).doubleValue();
    }

    private int getPowerPercentValue() {
        return ((Number) powerSpinner.getValue()).intValue();
    }

    private String getMemoOrDefault(String defaultMemo) {
        String memo = memoField.getText().trim();
        if (memo.isEmpty()) {
            return defaultMemo;
        }
        return memo;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "알림", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createSection(String title, Component content) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Colors.TEXT);
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createField(String labelText, Component input) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));

        JLabel label = new JLabel(labelText);
        label.setForeground(Colors.MUTED);
        label.setFont(new Font("Malgun Gothic", Font.BOLD, 12));

        input.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        input.setPreferredSize(new Dimension(10, 28));
        input.setMinimumSize(new Dimension(10, 28));

        panel.add(label, BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);
        panel.add(Box.createVerticalStrut(3), BorderLayout.SOUTH);
        return panel;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, Colors.BLUE, Color.WHITE);
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, new Color(236, 241, 247), Colors.TEXT);
        return button;
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        button.setFocusPainted(false);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(new EmptyBorder(7, 8, 7, 8));
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER),
                new EmptyBorder(12, 16, 11, 16)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Colors.MUTED);
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 13));

        valueLabel.setForeground(accentColor);
        valueLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void configureTable(JTable table) {
        table.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Malgun Gothic", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(232, 238, 246));
        table.getTableHeader().setForeground(Colors.TEXT);
        table.setGridColor(new Color(226, 232, 240));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(Colors.TEXT);
        table.setFillsViewportHeight(true);
    }

    private static String format(double value) {
        return String.format(Locale.US, "%.1f", value);
    }

    private enum DeviceStatus {
        NORMAL("정상"),
        WARNING("주의"),
        ABNORMAL("이상");

        private final String label;

        DeviceStatus(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private enum RecordType {
        STATUS("상태"),
        INSPECTION("점검");

        private final String label;

        RecordType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private static class Device {
        private final String deviceId;
        private final String name;
        private final String location;
        private double temperature;
        private double vibration;
        private int powerPercent;
        private DeviceStatus status = DeviceStatus.NORMAL;
        private LocalDateTime lastCheckedAt = LocalDateTime.now();

        private Device(String deviceId, String name, String location) {
            this.deviceId = deviceId;
            this.name = name;
            this.location = location;
        }

        private void updateStatusData(
                double temperature,
                double vibration,
                int powerPercent,
                DeviceStatus status,
                LocalDateTime checkedAt
        ) {
            this.temperature = temperature;
            this.vibration = vibration;
            this.powerPercent = powerPercent;
            this.status = status;
            this.lastCheckedAt = checkedAt;
        }

        private boolean isWarningOrAbnormal() {
            return status == DeviceStatus.WARNING || status == DeviceStatus.ABNORMAL;
        }

        private String getDisplayStatus() {
            return status.getLabel();
        }

        @Override
        public String toString() {
            return deviceId + " - " + name;
        }
    }

    private static class HistoryRecord {
        private final LocalDateTime recordedAt;
        private final RecordType recordType;
        private final String deviceId;
        private final String deviceName;
        private final double temperature;
        private final double vibration;
        private final int powerPercent;
        private final DeviceStatus status;
        private final String memo;

        private HistoryRecord(
                LocalDateTime recordedAt,
                RecordType recordType,
                String deviceId,
                String deviceName,
                double temperature,
                double vibration,
                int powerPercent,
                DeviceStatus status,
                String memo
        ) {
            this.recordedAt = recordedAt;
            this.recordType = recordType;
            this.deviceId = deviceId;
            this.deviceName = deviceName;
            this.temperature = temperature;
            this.vibration = vibration;
            this.powerPercent = powerPercent;
            this.status = status;
            this.memo = memo;
        }

        private Object[] toRowData() {
            return new Object[]{
                    recordedAt.format(HISTORY_TIME),
                    recordType.getLabel(),
                    deviceId,
                    deviceName,
                    format(temperature) + "°C",
                    format(vibration),
                    powerPercent + "%",
                    status,
                    memo
            };
        }
    }

    private class DeviceTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "장비명", "위치", "온도", "진동", "전원", "상태", "최근 확인"};

        @Override
        public int getRowCount() {
            return devices.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Device device = devices.get(rowIndex);
            if (columnIndex == 0) {
                return device.deviceId;
            }
            if (columnIndex == 1) {
                return device.name;
            }
            if (columnIndex == 2) {
                return device.location;
            }
            if (columnIndex == 3) {
                return format(device.temperature) + "°C";
            }
            if (columnIndex == 4) {
                return format(device.vibration);
            }
            if (columnIndex == 5) {
                return device.powerPercent + "%";
            }
            if (columnIndex == 6) {
                return device.status;
            }
            if (columnIndex == 7) {
                return device.lastCheckedAt.format(TABLE_TIME);
            }
            return "";
        }

        private void refresh() {
            fireTableDataChanged();
        }
    }

    private class HistoryTableModel extends AbstractTableModel {
        private final String[] columns = {"시간", "유형", "장비 ID", "장비명", "온도", "진동", "전원", "상태", "메모"};

        @Override
        public int getRowCount() {
            return historyRecords.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Object[] rowData = historyRecords.get(rowIndex).toRowData();
            return rowData[columnIndex];
        }

        private void refresh() {
            fireTableDataChanged();
        }
    }

    private class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Malgun Gothic", Font.BOLD, 12));
            label.setOpaque(true);

            if (value instanceof DeviceStatus) {
                DeviceStatus status = (DeviceStatus) value;
                label.setText(status.getLabel());
                if (!isSelected) {
                    label.setForeground(Color.WHITE);
                    if (status == DeviceStatus.NORMAL) {
                        label.setBackground(Colors.GREEN);
                    } else if (status == DeviceStatus.WARNING) {
                        label.setBackground(Colors.ORANGE);
                    } else {
                        label.setBackground(Colors.RED);
                    }
                }
            } else if (!isSelected) {
                label.setForeground(Colors.TEXT);
                label.setBackground(Color.WHITE);
            }

            return label;
        }
    }

    private static class Colors {
        private static final Color BACKGROUND = new Color(244, 247, 251);
        private static final Color NAVY = new Color(24, 45, 70);
        private static final Color TEXT = new Color(31, 41, 55);
        private static final Color MUTED = new Color(99, 116, 139);
        private static final Color BORDER = new Color(221, 228, 238);
        private static final Color BLUE = new Color(37, 99, 235);
        private static final Color GREEN = new Color(22, 163, 74);
        private static final Color ORANGE = new Color(234, 126, 28);
        private static final Color RED = new Color(220, 38, 38);
        private static final Color TEAL = new Color(13, 148, 136);
    }
}
