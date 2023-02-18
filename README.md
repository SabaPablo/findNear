# CBC News

## About

This is a native application developed in Kotlin, that shows to its users the headlines of the latest news from the CBC News API. 

## Features

- Retrofit and repositories.
- UI Home and items of recyclerView.
- Integration between the API response and the view. 
- Indicator of network connection loss.
- Filter to list items by type.
- Error handling.
- Persistence.
- App logo.
- Dynamic filters.
- Shimmer.
- Unit testing.


## Decisions made 

The first decision was which architecture to use. I chose to use MVVM as I have extensive experience on implementing this architecture. Another possibility was to use MVVM Clean Architecture. However my knowledge about this last one is less solid, and migration from one to the other should be easy.

I used Glide to load the images because it is able to show the images without internet connection.

In the beginning, I had the idea that there were only two filter keys, and I had implemented a static solution on the filtering options. Then, I noticed that there were more than two filter keys, so I decided to implement a search of the filter keys on the data received from the API. 

Another decision was to remove the news in the app's database after the app has received the updated news.

I decided not to implement the pagination in this version as the API used in this assignement is not prepared for this purpose. At a minimum, I need to indicate the number of news I want to have on the page and the page number. 

An option to implement the pagination, is to do it using the news saved in the app (in the device) from the last visit of the user. The problem with this is that the user will not see the news from the moments when the app was not used. When the users open the app, he or she will see the most updated news from the API, and the news from his/her last visit.

## Future improvements

- To migrate from MVVM architecture to MVVM Clean Architecture.
- To be able to access the news and not only the title.
