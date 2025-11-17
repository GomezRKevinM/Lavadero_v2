package com.lavadero.unicartagena.edu.co.domain.port.out.cliente;
import com.lavadero.unicartagena.edu.co.domain.model.personas.Cliente;
import java.util.List;
public interface ClienteRepository {
    Cliente guardar(Cliente cliente);
    Cliente actualizar(Cliente cliente);
    Cliente buscarPorId(Long id);
    List<Cliente> listarPorEmpresa(Long empresaId);
    List<Cliente> listarTodos();
    boolean eliminar(Long id);
    boolean existePorId(Long id);
}
