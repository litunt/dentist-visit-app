# dentist-visit-app
Test exercise for CGI

## Running the application
- to run the application first you have to wait until all imports are completed
- just in case open Maven menu from the right side panel
- from Lifecycle run clean and package to let all dependencies set up correcly
- check Java 8 JDK is set as project SDK as well as project language level is Java 8
- run application from `DentistAppApplication` class
- when console says Started Application, then go to http://localhost:8080

### Stage 0
- opened the project through pom.xml file so that IntelliJ would be able to run Maven prject build immediately
- during Spring Application startup there was no url in the console, so I just opened it at http://localhost:8080/

### Stage 1
- replaced dentist name input with dropdown selection to display all available dentists
- I had to change DentistVisitDTO structure and make it more like fields for filling the submission form of the visit
- also sending list of available dentist to the form to choose from
- added visitTime parameter to choose time of the visit
- added separate input for time choice
- changed input types to date and time correspondingly

### Stage 2
- created horizontal navbar with two tabs: Registration and Appointments
- Appointments page contains table that shows all dentist visit registrations divided into active and previous

### Stage 3
- added button "Details" next to appointment in each row to redirect to visit's detailed information page
- being on visit's detailed view, user can either remove or edit the registration (only active or future registration is editable)
- if chosen registration time is overlapping with already registered appointment to the same dentist, then registration process stops and corresponding error message is displayed

### Stage 4
- changed appointments page view so that there is search form
- user can search for appointments by various parameters (start date and/or time, and/or dentist)

### Stage 5
- added some simple test to check if the application logic works correctly

## Comments
- Even if I have read from Stage 5 that whatever improvements are for me to choose, I thought that since the base of the project has been given, I decided not to change it too significantly. I mean that, for example, I would like to use Angular for front-end part, however decided to proceed with given base (Thymeleaf), even despite the fact that it was actually my first experience with that. So, considering that, I would say that using Thymeleaf was the most challenging part. This is why I assume that some parts of solution may not be as perfect as I would like them to be
