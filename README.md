# Cars storage
<p>It is an online store for selling cars. You can see the backend part in this repository. 
The backend part interacts with the frontend part by REST API. You can see the code of the frontend part in the next repository: https://github.com/Andrey4281/car_storage_frontend.
In this project the following technologies were used: Spring Boot 2.0, Angular 8, Spring JPA, Spring Security (JWT), Spring REST, PostgreSQL, Liquibase.</p><br>
<hr>
<h3>The app has 6 screens:</h3><br>
<ul>
<li>Home page. Any user can see all adverts in the system, but can't edit it.<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/home.png"></li>
<li>Sign in page<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/sign_in.png"></li>
<li>Sign up page<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/sign_up.png"></li>
<li>Dialog window with info about each car and contacts of owner. Customer can see this information in the home page and call car owner to buy it<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/info_about_car.png"></li>
<li>Each owner after signing up has personal cabinet. Person can edit his adverts and create some new there<br>
  <img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/personal_page.png"></li>
<li>All adverts are editing or creating in the separed dialog window<br><img src="https://github.com/Andrey4281/car_storage_backend/blob/master/images/edit_create_dialog.png"></li>
</ul>
<hr>
<h3>Assembly:</h3><br>
<ul>
  <li> <strong>By maven</strong>
  <ol>
    <li>You should copy this project to you local machine by command git clone and fecth branch "master"</li>
    <li>You should define parameters of connection to your database in the file application.properties. If you use another database differs from PostgreSQL, you should add a dependency for your driver to pom.xml. Also you should define parameter file-path. This parameter defines folder in your disk where images of cars were saved. All images of cars are saved on the disk, not database</li>
    <li>You should move to the directory of the project and perform command mvn clean, and then perform command mvn package. The fronted part are already compiled to JavaScript code and placed in the dist folder. Packing maven-resources-plugin are used.</li>
  <li>Execute received jar by command: java -jar namejarfile.</li>
  <li>The last stage: open your favorite browser and enter url: http://localhost:8080</li>
  </ol>
  </li>
  <li> <strong>By Docker</strong>
    <ol>
      <li>You should install "Docker" and "Docker Compose" on your local machine</li>
      <li>You should copy this project to you local machine by command git clone and fecth branch "docker"</li>
      <li>You should pull needed docker images by commands:
        <ol>
          <li>docker pull andrey4281/car-storage-frontend:0.0.1 (reference on DockerHub: https://hub.docker.com/repository/docker/andrey4281/car-storage-frontend)</li>
          <li>docker pull andrey4281/cars_storage (reference on DockerHub:https://hub.docker.com/repository/docker/andrey4281/cars_storage)</li>
          <li>docker pull postgres:10.10</li>
        </ol>
      </li>
      <li>You should move to the directory of the project and perform command: docker-compose up -d</li>
      <li>Wait a bit time and then open your favorite browser and enter url: http://localhost:4200</li>
      <li>To stop and remove all containers perform command: docker-compose down. <strong>Pay attention after that all your data will be removed (database data and images). To avoid it you can configure volumes in docker-compose.yml file.</strong></li>
    </ol>
  </li>
</ul>
<hr>
<h3>TODO list:</h3><br>
<ul>
  <li>Add the exhaustive JavaDoc</li>
  <li>Add a mechanism for update jwt token (refresh token)</li>
</ul>
<hr>
<h3>Contacts:</h3><br>
<div>If you have any questions feel free to contact me. Email: Andrey4281@yandex.ru</div>
<div>Application on Heroku: https://infinite-mountain-37242.herokuapp.com/</div>
