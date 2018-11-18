# copyist
Global clipboard

This is a useful app for managing a group of elements that need to be maintained independently fo copying. It is a
standalone app (java swing), built with Spring Boot and Gradle.

To run directly from Gradle: gradle bootRun
To build an executable jar: gradle build

You can add copyable text to the list; give it a name, a position, and content. The name is how it will show in the list. The position is where it will show in the list (increasing integers). The content is what will be copied to the clipboard.

You can edit or delete existing items.

When you select an item in the list, its content is automatically copied to the clipboard to be available for pasting.

