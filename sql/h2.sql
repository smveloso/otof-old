select p.id,p.datetaken,p.filedigest, l.path from photo p inner join location l   on l.photo_id = p.id  where p.DATETAKEN < '2010-12-31' AND p.DATETAKEN > '2010-12-01' order by p.filedigest

select P.ID,L.ALBUM_ID , L.PATH, A.MOUNTPOINTASSTRING  
from PHOTO P inner join LOCATION L on P.id = L.photo_id
inner join LOCALFILESYSTEMALBUM A on A.id = L.album_id
where P.DATETAKEN is null
order by P.ID

--alter table FOTO add column (duplicate boolean);
--alter table FOTO add column (unidades varchar(255));

--delete from FOTO;
--select * from "PUBLIC".FOTO order by datatirada asc;
select count(*) from "PUBLIC".FOTO;
--select count(*) from "PUBLIC".FOTO where datatirada is  null;
--select count(*) from "PUBLIC".FOTO where duplicate;
--select digest, count(*) as "count" from "PUBLIC".FOTO  group by(digest) having "count" > 1 order by "count" desc;

--select * from "PUBLIC".FOTO where digest in 
--(select digest from "PUBLIC".FOTO  group by(digest) having count(*) > 1) order by digest;

--select * from "PUBLIC".FOTO order by digest asc;

--delete from FOTO where duplicate;

--update FOTO set unidades = 'DVD1;DVD2';
--update FOTO set unidades = null;

select count(*) from FOTO where unidades is null;

--select digest, count(*) as "count" from "PUBLIC".FOTO  group by(digest) order by "count" desc;
--select digest, count(*) as "count" from "PUBLIC".FOTO group by(digest) having "count" > 1;
--select arquivo, count(*) as "count" from "PUBLIC".FOTO group by(arquivo) having "count" > 1;
--select arquivo,digest,SUBSTRING(arquivo,LOCATE('\',arquivo,-1) + 1) as "nome" from "PUBLIC".FOTO order by "nome";
--select arquivo,digest,SUBSTRING(arquivo,LOCATE('\',arquivo,-1) + 1) as "nome" from "PUBLIC".FOTO order by digest;
