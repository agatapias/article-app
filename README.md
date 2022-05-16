# article-app
An app showing Articles using Spaceflight News API, written in Kotlin.

The app shows new scientific articles from various webpages, it automatically refreshes whenever new articles are published. 
The app allows the user to access the articles original websites, read some short description of them, and add selected articles to favourites for later use.


Features:
 - click on the article item to navigate to the original webpage of the article
 - double click on article item to add it to favourites
 - hold article item to view the article details


Description of code:
To list the articles, RecyclerView was used.

The layouts were made using one main activity layout and several fragments. 
Each fragment represents a seperate view, while the activity layout works as a container to hold the currently viewed layout.
The fragment layouts are: the main RecyclerView, Favourites tab, Article Details tab.

The recycler view layout was reused for the main list of articles and favrouties articles - with different designs for the two fragments.

The Recycler View is managed using Recycler View Holder and View Adapter. There, all the xml items are assigned to specific data gathered from the Spaceflight News API.

The data from the API is gathered using Retrofit. 

A database is used to store the articles liked by user in Favourites.
The database is build using Room db. 
I introduced several layers of accessing the database to avoid over complicated code in main fragment files. 
The layers are: 
  - creating the database in AppDatabase, 
  - defining dao and the main functions for quering data in ArticleDao, 
  - defining the repository ArticleRepository, which will will be used in code to create an instance
  - defining View Model where the query functions are called, and Coroutine Scope is applied where needed for asynchronous calling,
  - lastly, defining View Model Factory which returns an instance to be called in the fragment code
