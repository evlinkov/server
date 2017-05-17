Программа работает в режиме реального времени, и ждет HTTP запросов для категоризации товаров.  
Далее вызывается для каждого товара сам метод категоризации, который с помощью специального алгоритма для каждого товара определяет его категорию.

- В пакете [model](https://github.com/evlinkov/server/tree/master/src/main/java/ru/model) находятся классы, которые являются общими между сервером и приложением для общения между собой.  
- В пакете [entities](https://github.com/evlinkov/server/tree/master/src/main/java/ru/entities) находятся классы, которые описывают таблицы БД.  
- В пакете [dao](https://github.com/evlinkov/server/tree/master/src/main/java/ru/dao) находятся 2 интерфейса *CategoryDao* и *ProductDao* (там же находятся реализации этих интерфейсов), в которых объявлены методы, которые можно использовать для работы с БД.  
- В пакете [categorization](https://github.com/evlinkov/server/tree/master/src/main/java/ru/categorization) находится алгоритм для определения категории по данным от приложения.  
- В пакете [request](https://github.com/evlinkov/server/tree/master/src/main/java/ru/request) находятся классы, которые отвечают за работу с HTTP запросами.
- В пакете [bktree](https://github.com/evlinkov/server/tree/master/src/main/java/ru/bktree) находятся классы, которые отвечают за работу со словарем, который основан на данных о товарах.
- В пакете [distance](https://github.com/evlinkov/server/tree/master/src/main/java/ru/distance) находятся классы, которые отвечают за функцию, которая возвращает некоторое целое значение, которая обозначает насколько похожи 2 слова.      

Запуск сервера можно осуществить, например, такой командой : ***mvn clean install -Dmaven.tomcat.port=$port$ tomcat7:run-war*** , где под $port$ подставляется нужное значение порта. 
