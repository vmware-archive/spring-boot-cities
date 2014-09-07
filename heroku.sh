heroku create spring-boot-cities --stack cedar --buildpack http://github.com/heroku/heroku-buildpack-java.git
APP_PATH=`heroku config:get PATH`
heroku config:set PATH=/app/.jdk/bin:$APP_PATH
heroku addons:add heroku-postgresql:hobby-basic
git push heroku json-heroku:master
