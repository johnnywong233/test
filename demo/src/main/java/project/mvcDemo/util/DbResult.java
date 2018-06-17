package project.mvcDemo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class DbResult {
    private int affectedRows;
    private Serializable generatedKey;
}
