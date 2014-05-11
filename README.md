WOZapp
======

WOZapp is an android app which allows mobile-optimized access to the woz.ch news website.


### How WOZapp works

The app fetches, depending on the rubric, the woz.ch webpage HTML source code and parses out the list of articles. When one clicks on a article in the article list, the app goes to the article URL and fetches the content which is afterwards displayed in a WebView.

WOZapp does not cache data at the moment, which means all the fetched HTML source code is not saved locally on the device.


### Playstore
https://play.google.com/store/apps/details?id=ch.wozapp



### TODOs

* For the article WebView:
    1. Add font size selection
    2. Add font color selection
    3. Add background color selection
* Add caching for the app, so that it allows offline reading of the articles
* Add Dossier rubric 



