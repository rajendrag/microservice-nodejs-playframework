iVarsa-play
===================

A very simple PlayFramework application, to demonstrate how Play can be a backend server in a micro services architecture. There will be a front **Node** server (Please refer to https://github.com/rajendrag/ivarsa for the front server implementation).

Stack
-----------
- Play Framework
- MySQL
- Hibernate

Install Activator
------------------
Install latest version of activator from https://www.typesafe.com/activator/download unzip and add Activator to your PATH to have the command available in your terminal.

Setup
-----------
```
git clone https://github.com/rajendrag/ivarsa-play.git
cd ivarsa-play
activator "run"
```
This will start the application on port 9000 in auto reload mode, so that you can see your changes instantly, but as the UI is serving from Node server, when you start Node server it will run on 3000. So access http://localhost:3000 to open the applicaiton.

Load Testing
-------------
```
siege -c100 -t1M http://localhost:9000/units
```
This will create 100 concurrent requests and keep hitting the server for 1 minute and gives us the statistics

Contributers
-----------
- Rajendra
- Santhosh
