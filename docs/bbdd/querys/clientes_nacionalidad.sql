SELECT Nacionalidad, count(*) 
FROM cliente
GROUP BY Nacionalidad
ORDER BY Nacionalidad