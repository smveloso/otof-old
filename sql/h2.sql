--alter table FOTO add column (duplicate boolean);

--delete from FOTO;
--select * from "PUBLIC".FOTO order by datatirada asc;
select count(*) from "PUBLIC".FOTO;
--select count(*) from "PUBLIC".FOTO where datatirada is  null;
--select count(*) from "PUBLIC".FOTO where duplicate;
--select digest, count(*) as "count" from "PUBLIC".FOTO  group by(digest) having "count" > 1 order by "count" desc;


--select * from "PUBLIC".FOTO order by digest asc;

--delete from FOTO where duplicate;


--select digest, count(*) as "count" from "PUBLIC".FOTO  group by(digest) order by "count" desc;
--select digest, count(*) as "count" from "PUBLIC".FOTO group by(digest) having "count" > 1;
--select arquivo, count(*) as "count" from "PUBLIC".FOTO group by(arquivo) having "count" > 1;
--select arquivo,digest,SUBSTRING(arquivo,LOCATE('\',arquivo,-1) + 1) as "nome" from "PUBLIC".FOTO order by "nome";
--select arquivo,digest,SUBSTRING(arquivo,LOCATE('\',arquivo,-1) + 1) as "nome" from "PUBLIC".FOTO order by digest;