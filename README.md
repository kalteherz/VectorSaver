# VectorSaver
Library for work with GSON

Пример работы с библиотекой для сохранения и загрузки массива объектов — фигур из векторного редактора

СОХРАНЕНИЕ

	//создаём пустое хранилище объектов
	ObjectsContainer oc = new ObjectsContainer();
	//указываем автора программы, сохраняющей данные
	oc.authorName = "BolotinSE"; 
	
	//разбираем очередь с фигурами
	for (int i = 0; i < queue.getSize(); i++){
      //получаем текущую фигуру
      Shape shape = queue.getShape(i);
      //создаём новый item-объект класса текущей фигуры
      Item item = new Item(shape.getClass().getCanonicalName());
      //добавляем в item-объект свойство Color
      item.properties.add(new ItemProperty("Color", shape.color, Color.class));
      //добавляем в item-объект свойство массив точек Points
      item.properties.add(new ItemProperty("Points", shape.getAbsPoints(), ArrayList.class));
      //добавляем item-объект в хранилище объектов
      oc.items.add(item);
	}
	
	//сохраняем хранилище объектов в файл
	oc.saveToFile("MyShapes.B8219a");

ЗАГРУЗКА

	//создаём пустое хранилище объектов
	ObjectsContainer oc = new ObjectsContainer();
	//загружаем в хранилище данные из файла
	oc.loadFromFile(new File("MyShapes.b8219a"));
	
	//создаём строку для логирования ошибок
	StringBuilder log = new StringBuilder();
	Shape shape;
	//разбираем хранилище объектов
	for (Item item: oc.items){
        //определяем класс текущего item-объекта из хранилища
        //и создаём объект фигуры нужного класса 
	    switch (item.itemClass){
        case "Line":
            shape = new Line();
            break;
        case "Oval":
            shape = new Oval();
            break;
        case "Rect":
            shape = new Rect();
            break;
        case "Polygon":
            shape = new Polygon();
            break;
        case "Bezier":
            shape = new Bezier();
            break;
        default:
            log.append("Неизвестный класс '").append(item.itemClass).append("'").append(System.lineSeparator());
            continue;
	    } 
      
        //выбираем свойства из item-объекта и добавляем в фигуру
	    for (ItemProperty prop: item.properties){
        switch (prop.propertyName){
            case "Color":
                shape.color = prop.getProperty(Color.class);
                break;
            case "Points":
                shape.setAbsPoints(prop.getProperty(new TypeToken<ArrayList<Point>>(){}.getType()));
                break;
            default:
                log.append("Неизвестное свойство '").append(prop.propertyName).append("'").append(System.lineSeparator());
                continue;
        }
	    }
	    //добавляем текущую фигуру в очередь фигур
	    queue.addShape(shape);
	}
	
	
	//если строка логов не пустая, сообщаем об ошибках загрузки пользователю
	if (!log.toString().equals("")){
	    JOptionPane.showMessageDialog(this, log);
	}
