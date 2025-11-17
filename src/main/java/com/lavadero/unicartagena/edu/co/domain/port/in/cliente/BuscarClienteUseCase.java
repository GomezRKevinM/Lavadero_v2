package com.lavadero.unicartagena.edu.co.domain.port.in.cliente;
import com.lavadero.unicartagena.edu.co.domain.model.personas.Cliente;
import java.util.List;
public interface BuscarClienteUseCase {
    Cliente buscarPorId(Long id);
    List<Cliente> listarPorEmpresa(Long empresaId);
    List<Cliente> listarTodos();
}
