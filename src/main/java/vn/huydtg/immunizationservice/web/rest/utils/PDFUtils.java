package vn.huydtg.immunizationservice.web.rest.utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.pdf.PdfWriter;
import vn.huydtg.immunizationservice.domain.enumeration.VaccinationType;
import vn.huydtg.immunizationservice.service.dto.AppointmentCardDTO;
import vn.huydtg.immunizationservice.service.dto.AssignmentDTO;
import vn.huydtg.immunizationservice.service.dto.DiseaseDTO;
import vn.huydtg.immunizationservice.service.dto.EmployeeDTO;


public class PDFUtils {


    public static ByteArrayOutputStream generateAssignmentPdfStream(AppointmentCardDTO appointmentCardDTO, List<AssignmentDTO> assignmentDTOList, EmployeeDTO employeeDTO) throws DocumentException, IOException {
        String fontPath = "src/main/resources/font/NotoSerif-VariableFont_wdth,wght.ttf";


        List<String> diseasesList = assignmentDTOList.stream()
                .flatMap(a -> a.getVaccineLot().getVaccine().getDiseases().stream()
                        .map(DiseaseDTO::getName)
                )
                .toList();


        String diseaseGroup = String.join("; ", diseasesList);



        BaseFont customBaseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont14 = new Font(customBaseFont, 13, Font.BOLD, BaseColor.BLACK);
        Font titleFont18 = new Font(customBaseFont, 18, Font.BOLD, BaseColor.BLACK);
        Font contentFont = new Font(customBaseFont, 12, Font.NORMAL, BaseColor.DARK_GRAY);
        Font contentFontBolder = new Font(customBaseFont, 12, Font.BOLD, BaseColor.DARK_GRAY);
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();

        String cell1Data = appointmentCardDTO.getImmunizationUnit().getName() + "\n" + appointmentCardDTO.getImmunizationUnit().getAddress() + "\nHotline: " + appointmentCardDTO.getImmunizationUnit().getHotline() + "\n";
        String cell2Data =  "Mã bệnh nhân: " + "\n" + appointmentCardDTO.getPatient().getId() + "\n" + "Mã Phiếu tiêm chủng: " + appointmentCardDTO.getId() + "\n";

        PdfPTable table = new PdfPTable(2);
        float[] columnWidths = { 6f, 4f};
        table.setWidths(columnWidths);
        table.setWidthPercentage(100);
        PdfPCell cell1 = new PdfPCell(new Phrase(cell1Data, contentFont));

        cell1.setBorder(PdfPCell.NO_BORDER);
        PdfPCell cell2 = new PdfPCell(new Phrase(cell2Data, contentFont));

        cell2.setBorder(PdfPCell.NO_BORDER);

        table.addCell(cell1);
        table.addCell(cell2);

        document.add(table);

        document.add(new Phrase("\n"));
        Paragraph title = new Paragraph("PHIẾU CHỈ ĐỊNH\n\n", titleFont18);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        String gender = appointmentCardDTO.getPatient().getGender().equals(0) ? "Nam" : "Nữ";
        String patientInfoData = "Họ tên: " + appointmentCardDTO.getPatient().getFullName() +
                "           Ngày sinh: " + appointmentCardDTO.getPatient().getDateOfBirth() +
                "           Giới tính: " + gender + "\nĐịa chỉ: " + appointmentCardDTO.getPatient().getAddress() +
                "\nPhụ huynh/ Người giám sát (hệ thống): " + appointmentCardDTO.getPatient().getCustomer().getFullName() + "          Số điện thoại: " + appointmentCardDTO.getPatient().getCustomer().getPhone() +
                "\nPhụ huynh/ Người giám sát (hiện tại): ......................................             Số điện thoại: .................." +
                "\nBệnh/Nhóm bệnh: " + diseaseGroup + "\n";



        Paragraph patientInfo = new Paragraph(patientInfoData, contentFont);
        document.add(patientInfo);


        String physicianInfoData = "Bác sĩ chỉ định: " + employeeDTO.getFullName() + "\n" + "Mã nhân viên: " + employeeDTO.getEmployeeId() + "\n\n";



        Paragraph physicianInfo = new Paragraph(physicianInfoData, contentFont);
        document.add(physicianInfo);

        PdfPTable dataTable = new PdfPTable(7);
        dataTable.setWidthPercentage(100);
        float[] columnDataTableWidths = { 0.5f, 2.5f, 2f, 1f, 2f, 2f, 1f };
        dataTable.setWidths(columnDataTableWidths);


        PdfPCell dataTableCell1 = new PdfPCell(new Phrase("STT", contentFontBolder));
        PdfPCell dataTableCell2 = new PdfPCell(new Phrase("TÊN VẮC XIN", contentFontBolder));
        PdfPCell dataTableCell3 = new PdfPCell(new Phrase("SỐ LÔ", contentFontBolder));
        PdfPCell dataTableCell4 = new PdfPCell(new Phrase("LIỀU DÙNG", contentFontBolder));
        PdfPCell dataTableCell5 = new PdfPCell(new Phrase("ĐƯỜNG DÙNG ", contentFontBolder));
        PdfPCell dataTableCell6 = new PdfPCell(new Phrase("ĐƠN GIÁ", contentFontBolder));
        PdfPCell dataTableCell7 = new PdfPCell(new Phrase("HT", contentFontBolder));



        dataTableCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        dataTableCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        dataTableCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        dataTableCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        dataTableCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        dataTableCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
        dataTableCell7.setHorizontalAlignment(Element.ALIGN_CENTER);


        dataTable.addCell(dataTableCell1);
        dataTable.addCell(dataTableCell2);
        dataTable.addCell(dataTableCell3);
        dataTable.addCell(dataTableCell4);
        dataTable.addCell(dataTableCell5);
        dataTable.addCell(dataTableCell6);
        dataTable.addCell(dataTableCell7);
        int index = 0;
        for (AssignmentDTO assignmentDTO : assignmentDTOList) {
            ++index;
            String vaccinationType = assignmentDTO.getVaccineLot().getVaccinationType().equals(VaccinationType.DICH_VU) ? "TCDV" : "TCMR";
            PdfPCell dataLoopTableCell1 = new PdfPCell(new Phrase(String.valueOf(index), contentFont));
            PdfPCell dataLoopTableCell2 = new PdfPCell(new Phrase(assignmentDTO.getVaccineLot().getVaccine().getName(), contentFont));
            PdfPCell dataLoopTableCell3 = new PdfPCell(new Phrase(assignmentDTO.getVaccineLot().getLotCode(), contentFont));
            PdfPCell dataLoopTableCell4 = new PdfPCell(new Phrase(assignmentDTO.getDosage().toString() + "ml", contentFont));
            PdfPCell dataLoopTableCell5 = new PdfPCell(new Phrase(assignmentDTO.getRoute(), contentFont));
            PdfPCell dataLoopTableCell6 = new PdfPCell(new Phrase(assignmentDTO.getPrice().toString() + "VNĐ", contentFont));
            PdfPCell dataLoopTableCell7 = new PdfPCell(new Phrase(vaccinationType, contentFont));

            dataLoopTableCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataLoopTableCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataLoopTableCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataLoopTableCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataLoopTableCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataLoopTableCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataLoopTableCell7.setHorizontalAlignment(Element.ALIGN_CENTER);

            dataTable.addCell(dataLoopTableCell1);
            dataTable.addCell(dataLoopTableCell2);
            dataTable.addCell(dataLoopTableCell3);
            dataTable.addCell(dataLoopTableCell4);
            dataTable.addCell(dataLoopTableCell5);
            dataTable.addCell(dataLoopTableCell6);
            dataTable.addCell(dataLoopTableCell7);

        }

        document.add(dataTable);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        document.add(new Phrase("\nNgày: " + formattedDate + "\n"));
        PdfPTable confirmTable = new PdfPTable(2);
        float[] columnWidths55 = { 6f, 4f};
        confirmTable.setWidths(columnWidths55);
        confirmTable.setWidthPercentage(100);
        PdfPCell confirmTableCell1 = new PdfPCell(new Phrase("Chữ ký người giám sát/ phụ huynh\n", titleFont14));
        confirmTableCell1.setBorder(PdfPCell.NO_BORDER);
        PdfPCell confirmTableCell2 = new PdfPCell(new Phrase("Chữ ký bác sĩ chỉ định", titleFont14));
        confirmTableCell2.setBorder(PdfPCell.NO_BORDER);
        confirmTable.addCell(confirmTableCell1);
        confirmTable.addCell(confirmTableCell2);
        document.add(confirmTable);
        String t = "...................................................................................................................................................." +
                "..................................................................................................................................................................." +
                "...................................................................................................................................................................." +
                "................................................................................................................................................................................................................................................................................................................";
        document.add(new Phrase("\n\n\n\n\nHƯỚNG DẪN NHANH CHO KHÁCH HÀNG BƯỚC TIẾP THEO\n", titleFont14));
        document.add(new Phrase(t));
        document.close();
        return outputStream;
    }
}
