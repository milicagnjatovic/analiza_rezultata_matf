select i.indeks, count(*) polozenih from mg.ispit i join mg.student s on i.indeks=s.indeks where ocena>5 group by i.indeks order by polozenih
