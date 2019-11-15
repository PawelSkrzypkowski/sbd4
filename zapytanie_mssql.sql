create view top5_ogloszen as
select distinct top 5  op.idogloszenia, op.opis as 'Opis oferty', op.zarobki as 'Proponowane zarobki',
	k.nazwafirmy as 'Zleceniodawca', aplikacje.ilosc as 'Ilosc aplikacji' 
from dbo.ogloszeniepracownicze op
join (select count(*) as ilosc, ka.ogloszenie as ogloszenie from dbo.kandydat ka group by ka.ogloszenie) aplikacje on aplikacje.ogloszenie = op.idogloszenia
join dbo.klient k on op.zleceniodawca = k.idklienta
join dbo.kandydat przyjety_kandydat on (przyjety_kandydat.ogloszenie = op.idogloszenia and przyjety_kandydat.zaakceptowany = 1)
join dbo.osoba przyjeta_osoba on przyjeta_osoba.id = przyjety_kandydat.osoba
join dbo.znajomi znajomi_kandydaci on znajomi_kandydaci.znajomy1 = przyjeta_osoba.id or znajomi_kandydaci.znajomy2 = przyjeta_osoba.id
join dbo.kandydat znajomy_kandydat_przyjetego on znajomy_kandydat_przyjetego.ogloszenie = op.idogloszenia and 
				((znajomy_kandydat_przyjetego.osoba = znajomi_kandydaci.znajomy1 and znajomi_kandydaci.znajomy1 != przyjeta_osoba.id) or
				(znajomy_kandydat_przyjetego.osoba = znajomi_kandydaci.znajomy2 and znajomi_kandydaci.znajomy2 != przyjeta_osoba.id))
join dbo.osoba znajomy_przyjetego on znajomy_przyjetego.id = znajomy_kandydat_przyjetego.osoba
where (select count(*) from dbo.ogloszeniezainteresowanie oz where oz.ogloszenie = op.idogloszenia) between 3 and 5
	and aplikacje.ilosc = (select count(*) from dbo.kandydat ka where ka.ogloszenie = op.idogloszenia and dataaplikacji between '2018/01/01' and '2019/12/31')
	and k.dzialalnosc is not null
order by aplikacje.ilosc desc
