Prince App View
================

App | Settings
--- | ---

Earth View wallpaper...
Display a awesome image from Google Earth as your wallpaper.

- You can set as wallpaper,Profile picture,share,etc..

Instructions
------------

- Make sure that `Internet connection` is available
- Review us.


SomeLinks
------------

- https://earthview.withgoogle.com/dubai-united-arab-emirates-1779
- https://earthview.withgoogle.com/telfer-australia-2248
- https://earthview.withgoogle.com/la-chorrera-panama-6025
- https://earthview.withgoogle.com/_api/ngari-china-1008.json


For api call
- Thanks # http://square.github.io/retrofit/
- Thanks # https://realm.io/docs/java/latest/ & https://github.com/realm/realm-java
- https://code.tutsplus.com/tutorials/sending-data-with-retrofit-2-http-client-for-android--cms-27845

some ref links

https://realm.io/docs/java/latest/
https://news.realm.io/news/realm-for-android/
https://code.tutsplus.com/tutorials/sending-data-with-retrofit-2-http-client-for-android--cms-27845


- Pedometer - https://github.com/bagilevi/android-pedometer



- https://www.reddit.com/domain/earthview.withgoogle.com/


-----------------------------------
Api Call
-----------------------------------

GET
-------
https://earthview.withgoogle.com/_api/istanbul-turkey-1888.json


RESPONSE
------
{
id: "1888",
slug: "istanbul-turkey-1888",
url: "/istanbul-turkey-1888",
api: "/_api/istanbul-turkey-1888.json",
title: "Istanbul, Turkey â€“ Earth View from Google",
lat: "41.102942",
lng: "28.991188",
photoUrl: "https://www.gstatic.com/prettyearth/assets/full/1888.jpg",
thumbUrl: "https://www.gstatic.com/prettyearth/assets/preview/1888.jpg",
downloadUrl: "/download/1888.jpg",
region: "Istanbul",
country: "Turkey",
attribution: "Â©2014 CNES / Astrium, Cnes/Spot Image, DigitalGlobe",
mapsLink: "https://www.google.com/maps/@41.102942,28.991188,14z/data=!3m1!1e3",
mapsTitle: "View Istanbul, Turkey in Google Maps",
nextUrl: "/tuamotus-islands-french-polynesia-1032",
nextApi: "/_api/tuamotus-islands-french-polynesia-1032.json",
prevUrl: "/gobabeb-namibia-5583",
prevApi: "/_api/gobabeb-namibia-5583.json"
}


Pedometer
-----
Start app, press HOME. Service icon should be in the notification bar.
Start app, quit, start again. Service icon should appear and counters should work.

Given the app is counting steps
When I start the app from the IDE (so it's killed without running any callbacks)
And I press HOME
Then I should see the service icon in the notification bar.





License
-------

    Copyright (C) 2017 LWP-Prince App

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



