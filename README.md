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
    
    class Patient {
        - Person person
        - HealthPlan healthPlan
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
        - Patient patient
        - String time
        - Appointment appointment
    }
    
    Person "1" <-- "1" Patient : has
    Patient "1" *-- "1" HealthPlan : has
    Doctor "1" <-- "1" Person : has
    Doctor "1" <-- "n" Schedule : has
    Patient "1" <-- "n" Schedule : has
    Schedule "1" --> "1" Appointment : has

```
