package practica.compi2.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
public class CompileError {
    private final String phase;
    private final String message;
    private final int line;
    private final int column;

    @Override
    public String toString() {
        if (message.isEmpty()){
            return "No existe archivo abierto o esta en blanco revisa";
        }
        return null;
    }
}
