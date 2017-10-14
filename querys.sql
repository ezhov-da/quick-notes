-- drop table tasks

create table tasks
(
     id int identity    --идентификатор
    ,dateTask date  --дата внесения задачи
    ,textTask varchar   --текст задачи
    ,closeNotClose boolean  --закрыта задача или нет
    ,who int    -- что это, задача или дата 0 - дата, 1 - задача
    ,dateInsert timestamp   -- дата внесения задачи вместе со временем
    ,isDelete boolean default false
    ,color varchar(20) null --подсветка задачи
)

/** добавили столбец, который говорит о том, что пользователь удалил запись, но на самом деле просто ее скрываем*/
alter table tasks
drop column isDelete

alter table tasks
add isDelete boolean default false

select
    id
   ,dateTask
   ,textTask
   ,closeNotClose
   ,who
   ,dateInsert
from tasks


insert into tasks
(
   ,dateTask
   ,textTask
   ,closeNotClose
   ,who
   ,dateInsert
) 
values
(
    ,?
    ,?
    ,?
    ,?
    ,?
)

select dateTask from tasks where textTask = ?


select * from tasks
-- delete from tasks


select
     id  --идентификатор
    ,dateTask --дата внесения задачи
    ,textTask --текст задачи
    ,closeNotClose --закрыта задача или нет
    ,who  -- что это, задача или дата 0 - дата, 1 - задача
    ,dateInsert  -- дата внесения задачи вместе со временем
from tasks
order by 
    dateTask desc


select * from tasks

update tasks
    set closenotclose = true
where id = 26


update tasks
    set isDelete = true
where id = 26

