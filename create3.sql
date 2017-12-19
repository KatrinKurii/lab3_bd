delimiter $$
create procedure upd()
begin
update facts set amount=floor(rand()*10) where fid=18;
end;
$$	
delimiter $$
create trigger trig1 before update on ware
for each row begin
update facts set amount=OLD.wid where facts.wid=OLD.wid;
end;
$$
delimiter $$
create event evnt1 on schedule every 1 minute
do
call upd();
$$
delimiter $$
create event evnt2 on schedule every 1 second
do
call upd();
$$
SET GLOBAL event_scheduler = ON;
