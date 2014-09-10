heroku create cities-s12gx --stack cedar --buildpack http://github.com/heroku/heroku-buildpack-java.git
APP_PATH=`heroku config:get PATH`
heroku config:set PATH=/app/.jdk/bin:$APP_PATH
heroku config:set SPRING_CLOUD_APP_NAME=spring-boot-cities
heroku config:set SPRING_PROFILES_ACTIVE=cloud
heroku addons:add heroku-postgresql:hobby-basic
git push heroku json-heroku:master
