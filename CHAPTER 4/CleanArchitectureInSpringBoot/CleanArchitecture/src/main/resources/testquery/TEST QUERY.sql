create table charactor (
		id bigint auto_increment,
        level int,
        nickname varchar(100),
        job varchar(100),
		
		primary key (id)
);

insert into charactor(level, nickname, job) values (10, "diger1", "전사");
insert into charactor(level, nickname, job) values (30, "diger2", "파이터");