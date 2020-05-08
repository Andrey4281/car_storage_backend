# Cars storage
<p>It is online store for selling cars.. You can see backend's part in this repository. 
Backend part interacts with frontend part by REST API. You can see the code of the frontend part in the next repository: https://github.com/Andrey4281/car_storage_frontend.
In this project the following technologies were used: Spring Boot 2.0, Angular 8, Spring JPA, Spring Security (JWT), Spring REST, PostgreSQL, Liquibase.</p><br>
<hr>
<h3>The app has the 6 screens:</h3><br>
<ul>
<li>Home page. Any user can see all adverts in the system, but he can't edit it.<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/home.png"></li>
<li>Sign in page<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/sign_in.png"></li>
<li>Sign up page<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/sign_up.png"></li>
<li>Dialog window with info about each car and contacts of owner. Customer can see this information in the home page and call car owner to buy it<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/info_about_car.png"></li>
<li>Each owner after signing up has personal cabinet. He can edit his adverts and create some new there<br>
  <img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/personal_page.png"></li>
<li>All adverts are editing or creating in separed dialog window<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/edit_create_dialog.png"></li>
</ul>
<hr>
<h3>Assembly:</h3><br>
  <ol>
    <li>You should copy this project to you local machine by command git clone</li>
    <li>You should define parameters of connection to your database in the file application.properties. If you use another database differ from PostgreSQL, you should add dependency for your driver to pom.xml. Also you should define parameter file-path. This parameter defines folder in your disk where images of cars were saved. All images for cars are saved on the disk, not database</li>
    <li>You should move to directory of project and perform command mvn clean, and then perform command mvn package. Fronted part are already compiled to JavaScript code and placed in dist folder. For packing maven-resources-plugin are used.</li>
  <li>Execute received jar by command: java -jar namejarfile.</li>
  <li>The last stage open your favorite browser and enter url: http://localhost:8080/car_storage/home</li>
  </ol>
<hr>
<h3>TODO list:</h3><br>
