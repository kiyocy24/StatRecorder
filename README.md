# Statistics Recorder Plugin
![Build](https://github.com/kiyocy24/StatRecorder/workflows/Build/badge.svg)


**This plugin is under development.**

# Overview
This plugin is for minecraft.
User statistics can be stored in the database.
Right now, only the statistics are stored for items, but in the future it will be possible to store all the statistics.

# Requirement
- mysql

# How to set up
1. Mysql setting
    1. Install mysql
    2. Create a database and user for this plugin (Database name and user name can be decided freely)
        ```bash:mysql
       CREATE DATABASE stat_recorder;
       CREATE USER minecraft@localhost IDENTIFIED by 'password';
       GRANT ALL ON stat_recorder.* TO minecraft@localhost;
        ``` 
2. Place this plugin int `plugins` folder of your Minecraft server directory and start Minecraft server.
3. Set up each item in the `config.yml` file
    ```yml:config.yml
   mysql:
     host: localhost
     port: 3306
     database: stat_recorder
     username: minecraft
     password: password
    ```
4. Restart Minecraft Server
