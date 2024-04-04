# clinica-medica

## Diagrama de Classes

```mermaid
classDiagram
    class Person {
        - String name
        - int age
        - String gender
        - String motherName
    }
       
    class HealthPlan {
        - String name
        - String coverage
    }
    
    class Doctor {
        - Person person
        - String specialty
    }
    
    class Appointment {
        - String consultation
        - String surgery
    }
    
    class Schedule {
        - Doctor doctor
        - String specialty
        - Person person
        - String time
        - Appointment appointment
    }
    
    Doctor "1" <-- "1" Person : has
    Doctor "1" <-- "n" Schedule : has
    Person "1" <-- "n" Schedule : has
    Schedule "1" --> "1" Appointment : has
    Schedule "1" --> "1" HealthPlan : has
```
