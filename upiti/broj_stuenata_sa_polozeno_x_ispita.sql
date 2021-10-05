with tmp as (
select i.indeks, count(*) polozenih 
from mg.ispit i join mg.student s on i.indeks=s.indeks 
    join mg.predmet p on p.id = i.idpredmeta 
where ocena>5 and i.indeks like '%/2018%' and semestar in (5,6)
group by i.indeks )
select polozenih polozeno_ispita, count(*) broj_studenata
from tmp
group by polozenih
order by polozeno_ispita desc;
