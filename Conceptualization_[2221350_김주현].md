<div align="center">

### 장비 상태 모니터링 및 점검 지원 시스템  
### Device Monitoring and Inspection Support System
### 1. Conceptualization

<img src="/images/cover.png" alt="cover" width="500"/>

</div>

<br>

## Student Information

<div align="center">

<table>
  <tr>
    <th align="center" width="180">Student No</th>
    <th align="center" width="180">Name</th>
    <th align="center" width="260">E-mail</th>
  </tr>
  <tr>
    <td align="center">22213501</td>
    <td align="center">김주현</td>
    <td align="center">kimjuhyeon@yu.ac.kr</td>
  </tr>
</table>

</div>

<br>

## [ Revision history ]

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Revision date</th>
    <th align="center" width="120">Version #</th>
    <th align="center" width="360">Description</th>
    <th align="center" width="160">Author</th>
  </tr>
  <tr>
    <td align="center">03/27/2026</td>
    <td align="center">1.0.0</td>
    <td align="center">First Documentation</td>
    <td align="center">김주현</td>
  </tr>
</table>

</div>

<br>

## [ Contents ]

<div align="center">

<table>
  <tr>
    <td width="70" align="center">1</td>
    <td width="630"><a href="#1-business-purpose">Business purpose</a></td>
  </tr>
  <tr>
    <td align="center">2</td>
    <td><a href="#2-system-context-diagram">System context diagram</a></td>
  </tr>
  <tr>
    <td align="center">3</td>
    <td><a href="#3-use-case-list">Use case list</a></td>
  </tr>
  <tr>
    <td align="center">4</td>
    <td><a href="#4-concept-of-operation">Concept of operation</a></td>
  </tr>
  <tr>
    <td align="center">5</td>
    <td><a href="#5-problem-statement">Problem statement</a></td>
  </tr>
  <tr>
    <td align="center">6</td>
    <td><a href="#6-glossary">Glossary</a></td>
  </tr>
  <tr>
    <td align="center">7</td>
    <td><a href="#7-references">References</a></td>
  </tr>
</table>

</div>

---

<br>

# 1. Business purpose

## 1) Project background

장비를 여러 대 동시에 운용하거나 관리하는 환경에서는 각 장비의 상태를 빠르게 확인하고, 이상이 발생했을 때 즉시 대응하는 것이 중요하다. 그러나 실제 운용 환경에서는 장비 상태를 하나씩 확인해야 하는 경우가 많고, 점검 결과나 상태 기록도 따로 관리해야 하므로 시간이 오래 걸리고 관리가 번거로울 수 있다.

특히 장비 수가 많아질수록 현재 어떤 장비에 이상이 있는지 바로 파악하기 어렵고, 과거에 어떤 문제가 반복되었는지 확인하는 것도 쉽지 않다. 이상 상태를 놓치면 점검 시점이 늦어질 수 있고, 기록이 체계적으로 남지 않으면 이후에 원인을 추적하거나 장비별 경향을 비교하는 것도 어려워진다.

이러한 문제를 줄이기 위해 장비 상태를 한곳에서 확인하고, 이상 발생 시 경고를 제공하며, 점검 결과와 상태 기록을 저장하고 다시 조회할 수 있는 시스템이 필요하다. 본 프로젝트는 이러한 필요를 바탕으로 장비 상태 모니터링과 점검 지원 과정을 하나의 시스템 안에서 관리할 수 있도록 하는 것을 목표로 한다.

## 2) Goal

- 장비 상태를 한눈에 확인하고, 이상이 발생했을 때 빠르게 대응할 수 있는 환경을 구성한다.  
- 점검 결과와 상태 기록을 저장하여 이후에도 이력과 통계를 확인할 수 있게 한다.  
- 이를 통해 장비 상태 모니터링과 점검 과정을 보다 효율적으로 지원할 수 있도록 한다.

## 3) Target market

- 여러 장비를 관리해야 하는 운용자  
- 장비 등록, 점검 이력 관리, 통계 확인이 필요한 관리자  
- 장비 상태 모니터링과 점검 지원 기능이 필요한 관리 환경

</div>

---

<br>

# 2. System context diagram

<div align="center">
  <img src="/images/system-context-diagram.png" alt="system-context-diagram" width="700"/>
</div>

<br>

- **Provide status data** : 상태 데이터 제공  
- **Alert notification** : 이상 알림  
- **Execute inspection** : 점검 수행  
- **Input status data** : 상태 데이터 입력  
- **Register device** : 장비 등록  
- **Query device list** : 장비 목록 조회  
- **Query history data** : 이력 데이터 조회  
- **Query statistics data** : 통계 데이터 조회  

---

<br>

# 3. Use case list

본 프로젝트에서 발견할 수 있는 use cases는 다음과 같다.

## 3.1. Provide status data

<div align="center">

<table>
  <tr>
    <td align="center" width="180"><b>Actor</b></td>
    <td width="720">Device</td>
  </tr>
  <tr>
    <td align="center"><b>Description</b></td>
    <td>Device가 시스템에 상태 데이터를 제공한다.</td>
  </tr>
</table>

</div>

## 3.2. Alert notification

<div align="center">

<table>
  <tr>
    <td align="center" width="180"><b>Actor</b></td>
    <td width="720">System</td>
  </tr>
  <tr>
    <td align="center"><b>Description</b></td>
    <td>시스템이 이상 상태를 감지하면 User에게 경고를 전달한다.</td>
  </tr>
</table>

</div>

## 3.3. Execute inspection

<div align="center">

<table>
  <tr>
    <td align="center" width="180"><b>Actor</b></td>
    <td width="720">User</td>
  </tr>
  <tr>
    <td align="center"><b>Description</b></td>
    <td>User가 장비 점검을 수행한다.</td>
  </tr>
</table>

</div>

## 3.4. Input status data

<div align="center">

<table>
  <tr>
    <td align="center" width="180"><b>Actor</b></td>
    <td width="720">User</td>
  </tr>
  <tr>
    <td align="center"><b>Description</b></td>
    <td>User가 상태 데이터를 시스템에 입력한다.</td>
  </tr>
</table>

</div>

## 3.5. Register device

<div align="center">

<table>
  <tr>
    <td align="center" width="180"><b>Actor</b></td>
    <td width="720">User</td>
  </tr>
  <tr>
    <td align="center"><b>Description</b></td>
    <td>User가 새로운 장비를 시스템에 등록한다.</td>
  </tr>
</table>

</div>

## 3.6. Query device list

<div align="center">

<table>
  <tr>
    <td align="center" width="180"><b>Actor</b></td>
    <td width="720">User</td>
  </tr>
  <tr>
    <td align="center"><b>Description</b></td>
    <td>User가 시스템에 장비 목록 조회를 요청한다.</td>
  </tr>
</table>

</div>

## 3.7. Query history data

<div align="center">

<table>
  <tr>
    <td align="center" width="180"><b>Actor</b></td>
    <td width="720">User</td>
  </tr>
  <tr>
    <td align="center"><b>Description</b></td>
    <td>User가 시스템에 이력 데이터 조회를 요청한다.</td>
  </tr>
</table>

</div>

## 3.8. Query statistics data

<div align="center">

<table>
  <tr>
    <td align="center" width="180"><b>Actor</b></td>
    <td width="720">User</td>
  </tr>
  <tr>
    <td align="center"><b>Description</b></td>
    <td>User가 시스템에 통계 데이터 조회를 요청한다.</td>
  </tr>
</table>

</div>

---

<br>

# 4. Concept of operation

## 4.1. Provide status data

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Item</th>
    <th align="center" width="730">Description</th>
  </tr>
  <tr>
    <td align="center">Purpose</td>
    <td>시스템이 장비 상태를 반영할 수 있도록 상태 데이터를 제공받는다.</td>
  </tr>
  <tr>
    <td align="center">Approach</td>
    <td>Device가 상태 데이터를 전달하면 시스템이 이를 수신하고 처리한다.</td>
  </tr>
  <tr>
    <td align="center">Dynamics</td>
    <td>장비가 상태 데이터를 보내는 경우</td>
  </tr>
  <tr>
    <td align="center">Goals</td>
    <td>현재 장비 상태를 시스템에 반영할 수 있도록 한다.</td>
  </tr>
</table>

</div>

## 4.2. Alert notification

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Item</th>
    <th align="center" width="730">Description</th>
  </tr>
  <tr>
    <td align="center">Purpose</td>
    <td>이상 상태가 발생했을 때 User에게 이를 알린다.</td>
  </tr>
  <tr>
    <td align="center">Approach</td>
    <td>시스템이 상태 데이터를 분석하고 이상이 감지되면 경고를 생성한다.</td>
  </tr>
  <tr>
    <td align="center">Dynamics</td>
    <td>이상 상태가 발생하는 경우</td>
  </tr>
  <tr>
    <td align="center">Goals</td>
    <td>User가 점검이 필요한 상황을 빠르게 확인할 수 있도록 한다.</td>
  </tr>
</table>

</div>

## 4.3. Execute inspection

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Item</th>
    <th align="center" width="730">Description</th>
  </tr>
  <tr>
    <td align="center">Purpose</td>
    <td>User가 특정 장비에 대한 점검을 수행할 수 있도록 한다.</td>
  </tr>
  <tr>
    <td align="center">Approach</td>
    <td>User가 점검을 요청하면 시스템이 점검 절차를 수행하고 결과를 처리한다.</td>
  </tr>
  <tr>
    <td align="center">Dynamics</td>
    <td>장비 점검을 수행하는 경우</td>
  </tr>
  <tr>
    <td align="center">Goals</td>
    <td>점검 결과를 확인하고 이후 기록에 활용할 수 있도록 한다.</td>
  </tr>
</table>

</div>

## 4.4. Input status data

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Item</th>
    <th align="center" width="730">Description</th>
  </tr>
  <tr>
    <td align="center">Purpose</td>
    <td>User가 상태 데이터를 직접 입력할 수 있도록 한다.</td>
  </tr>
  <tr>
    <td align="center">Approach</td>
    <td>User가 상태 값을 입력하면 시스템이 이를 수신하고 처리한다.</td>
  </tr>
  <tr>
    <td align="center">Dynamics</td>
    <td>상태 데이터를 직접 입력하는 경우</td>
  </tr>
  <tr>
    <td align="center">Goals</td>
    <td>수동 입력된 상태 데이터도 시스템에 반영할 수 있도록 한다.</td>
  </tr>
</table>

</div>

## 4.5. Register device

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Item</th>
    <th align="center" width="730">Description</th>
  </tr>
  <tr>
    <td align="center">Purpose</td>
    <td>User가 새로운 장비를 시스템에 등록할 수 있도록 한다.</td>
  </tr>
  <tr>
    <td align="center">Approach</td>
    <td>장비 정보를 입력하면 시스템이 이를 등록하고 관리 대상에 포함한다.</td>
  </tr>
  <tr>
    <td align="center">Dynamics</td>
    <td>새로운 장비를 등록하는 경우</td>
  </tr>
  <tr>
    <td align="center">Goals</td>
    <td>관리 대상 장비를 체계적으로 관리할 수 있도록 한다.</td>
  </tr>
</table>

</div>

## 4.6. Query device list

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Item</th>
    <th align="center" width="730">Description</th>
  </tr>
  <tr>
    <td align="center">Purpose</td>
    <td>User가 현재 등록된 장비 목록을 확인할 수 있도록 한다.</td>
  </tr>
  <tr>
    <td align="center">Approach</td>
    <td>User가 목록 조회를 요청하면 시스템이 장비 목록을 제공한다.</td>
  </tr>
  <tr>
    <td align="center">Dynamics</td>
    <td>장비 목록을 조회하는 경우</td>
  </tr>
  <tr>
    <td align="center">Goals</td>
    <td>전체 장비 현황을 쉽게 파악할 수 있도록 한다.</td>
  </tr>
</table>

</div>

## 4.7. Query history data

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Item</th>
    <th align="center" width="730">Description</th>
  </tr>
  <tr>
    <td align="center">Purpose</td>
    <td>User가 점검 및 상태 이력을 확인할 수 있도록 한다.</td>
  </tr>
  <tr>
    <td align="center">Approach</td>
    <td>User가 이력 조회를 요청하면 시스템이 저장된 기록을 제공한다.</td>
  </tr>
  <tr>
    <td align="center">Dynamics</td>
    <td>이력 데이터를 조회하는 경우</td>
  </tr>
  <tr>
    <td align="center">Goals</td>
    <td>과거 기록과 반복되는 문제를 추적할 수 있도록 한다.</td>
  </tr>
</table>

</div>

## 4.8. Query statistics data

<div align="center">

<table>
  <tr>
    <th align="center" width="170">Item</th>
    <th align="center" width="730">Description</th>
  </tr>
  <tr>
    <td align="center">Purpose</td>
    <td>User가 장비 통계를 확인할 수 있도록 한다.</td>
  </tr>
  <tr>
    <td align="center">Approach</td>
    <td>User가 통계 조회를 요청하면 시스템이 누적 데이터를 집계하여 제공한다.</td>
  </tr>
  <tr>
    <td align="center">Dynamics</td>
    <td>통계 데이터를 조회하는 경우</td>
  </tr>
  <tr>
    <td align="center">Goals</td>
    <td>문제가 자주 발생하는 장비나 항목을 파악할 수 있도록 한다.</td>
  </tr>
</table>

</div>

---

<br>

# 5. Problem statement

## 5.1 Overview

본 시스템은 장비 상태를 빠르게 확인하고, 이상 상태가 발생했을 때 즉시 대응할 수 있도록 돕는 것을 목표로 한다. 또한 점검 결과와 상태 기록을 저장하여 이후에도 이력과 통계를 확인할 수 있도록 하는 것이 목적이다.

이 시스템은 상태 데이터 수집, 이상 알림, 점검 수행, 결과 저장, 이력 조회, 통계 제공 기능을 수행해야 하며, 이를 구현하는 과정에서 다음과 같은 문제들을 고려해야 한다.

## 5.2 Problem #1 – Accuracy of status data

장비 상태 데이터가 정확하게 반영되어야 한다. 상태 데이터가 잘못 입력되거나 기록이 누락되면 실제 상태와 다른 결과를 보여줄 수 있고, 이상 알림이나 점검 결과 역시 신뢰하기 어려워진다.

## 5.3 Problem #2 – Alert delivery

이상 상태를 빠르게 전달할 수 있어야 한다. 경고가 늦게 전달되거나 누락되면 User가 필요한 시점에 점검을 수행하지 못할 수 있다. 따라서 시스템은 이상 발생 시 이를 명확하게 보여줄 수 있어야 한다.

## 5.4 Problem #3 – History management

점검 결과와 상태 기록은 이후에도 다시 조회할 수 있어야 한다. 기록이 제대로 저장되지 않으면 과거 이력을 추적하거나 장비별 문제 경향을 확인하기 어렵다.

## 5.5 Problem #4 – Technical difficulty in implementation

실제 장비와 직접 연결하는 것은 구현 단계에서 어렵다. 따라서 본 프로젝트에서는 가상의 장비와 데이터를 사용하여 시스템을 단순화하되, 이후 실제 장비에도 적용할 수 있는 구조를 고려해야 한다.

---

<br>

# 6. Glossary

<div align="center">

<table>
  <tr>
    <th align="center" width="240">Term</th>
    <th align="center" width="660">Description</th>
  </tr>
  <tr>
    <td align="center">Monitoring</td>
    <td>장비 상태를 계속 확인하는 과정</td>
  </tr>
  <tr>
    <td align="center">Inspection</td>
    <td>장비 상태를 점검하고 이상 여부를 판단하는 과정</td>
  </tr>
  <tr>
    <td align="center">Status data</td>
    <td>장비의 현재 상태를 나타내는 데이터</td>
  </tr>
  <tr>
    <td align="center">Alert notification</td>
    <td>이상 상태가 발생했을 때 사용자에게 전달되는 알림</td>
  </tr>
  <tr>
    <td align="center">Query</td>
    <td>시스템에 필요한 정보를 요청하는 동작</td>
  </tr>
  <tr>
    <td align="center">Device list</td>
    <td>등록된 장비들의 목록 정보</td>
  </tr>
  <tr>
    <td align="center">History data</td>
    <td>과거 점검 결과와 상태 기록에 대한 데이터</td>
  </tr>
  <tr>
    <td align="center">Statistics data</td>
    <td>누적된 기록을 바탕으로 정리한 통계 데이터</td>
  </tr>
</table>

</div>

---

<br>

# 7. References

