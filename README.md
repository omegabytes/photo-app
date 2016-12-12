#Photo Album Using Flikr
###CSC 413
##### Evan Terry and Alex Gaesser


* Build an online photo album application that access photos from flickr.com. [video](https://vimeo.com/193262006)

* For the Test button only, see this demo [video.](https://vimeo.com/194252110)

* Follow the GUI layout shown in the demo video. If you’d like to customize the application view, you must get permission from Dr. Hsu first.

The only saved state in the Online Photo Album is a text file that contains a list of URLs for images that have been selected for the album. (You can name this file anything you want.)
There are two textboxes in the application. The left box contains the tag to search for; note that the tag may contain spaces. (Hint: each space should be replaced with %20 in the url search string.) The right box contains the max number of search results to display.

A list of the UI actions and their outcomes follows; this will be used as a rubric for grading your project. (Points for each operation are included; projects that do not compile will not receive points.)

1. User clicks Test button *5 points*
  * Interpret the text in the left textbox as a url, get the image at that url 
  * Scale the image to height=200 pixels, while preserving aspect ratio 
  * Add the image to the scrollpane
2. User clicks Search button *25 points*
  * The Flickr photo database is searched for the tag displayed in the left textbox (Note: make sure a tag with spaces is supported correctly! Replace a space with “%20”)
  * The number of search results returned is no more than the number entered in the right textbox
  * Scale each search result image to height=200 pixels, while preserving aspect ratio
  * All returned search results are added to and displayed on the scrollpane at the top of the window, below the previously displayed photos
  * The URL of each photo is printed on the console
3. User clicks Save button *10 points*
  * URLs of all photos currently displayed in the scrollpane are saved in a file (this is the saved “photo album”)
  * The URL of each saved photo is printed on the console
  * *(Note: to specify the file of URLs, you should use a relative file path. If you use an absolute file path, your code will most likely break when executed on another user’s machine.)*
4. User clicks Exit button *5 points*
  * Application exits
5. User clicks Load button *10 points*
  * Load photos from the previously saved photo album (actually a list of saved URLs)
  * Add and display each loaded photo on the scrollpane
  * The URL of each loaded photo is printed on the console
6. User clicks on a photo *10 points*
  * Photo is selected
  * The index of the selected photo (i.e., its order in the photos displayed in the scrollpane, from top to bottom, starting at 0) is printed on the console (ex., “Selected photo 3”)
7. User clicks Delete button *25 points*
  * Selected photo is deleted (i.e., no longer displayed in scrollpane)
  * The index of the deleted photo is printed on the console (ex. “Deleted photo 4”)
8. Documentation and style *10 points*
  * Details TBD
