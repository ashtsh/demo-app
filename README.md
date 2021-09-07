# demo-app

## APIs

 - `/get` : Displays results fetched from api in descending order of publish time
 - `/search`: Displays search results queried on 'title' and 'description' field of video data

## Features

 - App starts scheduler in background which queries youtube API every 11 secs for search term 'cricket'
 - React-app to display results
 - MySQL DB is used to store the data received 
	 - **Primary Key** : video_id of the youtube video
	 - **Secondary Index** : publish_time to enable quick order-by descending results. (index is also descending) 
 - Pagination is used for fetching the results on `/get` on both front-side and back-end side.
 - Lucene is used for full text-search on 'title' and 'description' fields. 
 - Home page provide supports for adding KEYS. App automatically uses another one if available in case current key exhausts.
## Installation
 
     git clone https://github.com/ashtsh/demo-app.git
     cd demo-app
     docker-compose up --build

## How to run

 - Go to http://localhost:3000/
 - Backend APIs are available at `localhost:8080/get` and `localhost:8080/search`
