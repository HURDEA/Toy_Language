package interpreter.model.stmt;

import interpreter.model.PrgState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
    }

    @Test
    void nopDoesNotChangeProgramState() {
        PrgState result = new NopStmt().execute(state);

        assertSame(state, result);
    }
}
