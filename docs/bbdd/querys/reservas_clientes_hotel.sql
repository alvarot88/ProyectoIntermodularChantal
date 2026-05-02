SELECT c.nombre, c.apellidos, r.fecha_reserva, r.estado_reserva, rh.fecha_entrada, rh.fecha_salida,ht.nombre
FROM reserva r
INNER JOIN cliente c ON r.id_cliente =c.id_cliente
INNER JOIN reserva_habitacion rh ON rh.id_reserva= r.id_reserva
INNER JOIN habitacion h ON h.id_habitacion=rh.id_habitacion
INNER JOIN hotel ht ON ht.id_hotel=h.id_hotel
WHERE ht.pais='Portugal'
