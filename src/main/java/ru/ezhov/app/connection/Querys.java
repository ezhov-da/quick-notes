package ru.ezhov.app.connection;

/**
 *
 * @author Денис
 */
public final class Querys {

    /**
     * вносим задачу
     */
    public static final String INSERT_TASK
            = "insert into tasks\n"
            + "(\n"
            + "    dateTask\n"
            + "   ,textTask\n"
            + "   ,closeNotClose\n"
            + "   ,who\n"
            + "   ,dateInsert\n"
            + ") \n"
            + "values\n"
            + "(\n"
            + "     ?\n"
            + "    ,?\n"
            + "    ,?\n"
            + "    ,?\n"
            + "    ,?\n"
            + ")";
    /**
     * проверяем есть ли задача за вносимую дату
     */
    public static final String SELECT_CHECK_DATE
            = "select dateTask from tasks where textTask = ?";

    /**
     * получаем весь список задач
     */
    public static final String SELECT_TASKS
            = "select\n"
            + "     id  --идентификатор\n"
            + "    ,dateTask --дата внесения задачи\n"
            + "    ,textTask --текст задачи\n"
            + "    ,closeNotClose --закрыта задача или нет\n"
            + "    ,who  -- что это, задача или дата 0 - дата, 1 - задача\n"
            + "    ,dateInsert  -- дата внесения задачи вместе со временем\n"
            + "     ,color\n"
            + "from tasks\n"
            + " where isDelete = false "
            + "order by \n"
            + "    dateTask desc\n"
            + "";

    /**
     * закрываем или открываем задачу
     */
    public static final String UPDATE_CLOSE
            = "update tasks\n"
            + "    set closenotclose = ?\n"
            + "where id = ?\n"
            + "\n"
            + "";

    /**
     * удаляем задачу, на самом деле просто ее скрываем
     */
    public static final String UPDATE_DELETE
            = "update tasks\n"
            + "    set isDelete = ?\n"
            + "where id = ?\n"
            + "\n"
            + "";
    
    public static final String UPDATE_TEXT
            = "update tasks\n"
            + "    set textTask = ?\n"
            + "where id = ?\n"
            + "\n"
            + "";    
    
    public static final String UPDATE_COLOR
            = "update tasks\n"
            + "    set color = ?\n"
            + "where id = ?\n"
            + "\n"
            + "";      
}
