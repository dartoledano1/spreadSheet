package engine.api;
import engine.impl.SheetDTO;

import java.io.*;
import java.util.Map;

public interface VersionsHandler {

    public static byte[] serializeSheetDTO(SheetDTO sheetDTO) throws IOException {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(byteOut)) {
            out.writeObject(sheetDTO);
            return byteOut.toByteArray();
        }
    }
    public static SheetDTO deserializeSheetDTO(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
             ObjectInputStream in = new ObjectInputStream(byteIn)) {
            return (SheetDTO) in.readObject();
        }
    }
}
