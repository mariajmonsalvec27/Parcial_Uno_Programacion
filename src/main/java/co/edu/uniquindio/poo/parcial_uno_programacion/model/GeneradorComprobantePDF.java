package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IGeneradorComprobable;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

//genera el comprobante de pago en PDF usando la libreria OpenPDF
public class GeneradorComprobantePDF implements IGeneradorComprobable {

    @Override
    public String getFormato() {
        return "PDF";
    }

    @Override
    public String getExtension() {
        return "pdf";
    }

    @Override
    public void generar(Matricula matricula, Path destino) throws IOException {
        Academia academia = Academia.getInstance();
        Document documento = new Document();
        try (OutputStream salida = new FileOutputStream(destino.toFile())) {
            PdfWriter.getInstance(documento, salida);
            documento.open();
            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            documento.add(new Paragraph(academia.getNombre() + " - Comprobante de pago", titulo));
            documento.add(new Paragraph("NIT " + academia.getNit() + " | " + academia.getDireccion()));
            documento.add(new Paragraph(academia.getTelefono() + " | " + academia.getCorreo() + " | " + academia.getPaginaWeb()));
            documento.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(2);
            for (String[] fila : filasComprobante(matricula)) {
                tabla.addCell(fila[0]);
                tabla.addCell(fila[1]);
            }
            documento.add(tabla);
            documento.close();
        } catch (DocumentException e) {
            throw new IOException("No se pudo generar el PDF", e);
        }
    }
}
