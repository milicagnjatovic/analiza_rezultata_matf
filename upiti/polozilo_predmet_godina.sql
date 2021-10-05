select i.idpredmeta, p.naziv, count(*) num, avg(ocena*1.0)^Crom mg.ispit i join mg.predmet p on p.id=i.idpredmeta where semestar in (5,6) and i.ocena>5 group by i.idpredmeta, p.naziv order by num
