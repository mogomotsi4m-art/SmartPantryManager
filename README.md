# Smart Pantry Manager
Android pantry and recipe suggestion app written in Java. SQLite stores pantry items and a seeded collection of 20 recipes.

## Features
- Pantry ingredient create, read, update, delete (CRUD)
- Optional expiry dates and input validation
- 20 recipes seeded during initial database creation
- Strict recipe suggestions: all required ingredients and quantities must be available
- Basic singular/plural normalisation and supported mass, volume, count conversions
- Recipe detail screen and persistent settings
- RecyclerView with custom adapters

## Technologies
Java, Android SDK (min SDK 24), SQLiteOpenHelper, AndroidX AppCompat, RecyclerView, JUnit 4.

## Run
Open this folder in Android Studio, wait for Gradle sync, connect an Android device with USB debugging, choose the device, and press Run.

## Test
Run `gradlew.bat test` on Windows. Unit tests cover full match, missing ingredient, insufficient quantity, plural normalisation, compatible conversion, empty pantry, and unknown units.

## Database
DatabaseHelper creates pantry, recipes, and recipe_ingredients tables. Pantry records persist on-device. Only known compatible units are converted; mass and volume are not treated as interchangeable.

Capture actual screenshots of the running app before submission. Verify all manual test cases and update documentation to reflect observed behaviour.
