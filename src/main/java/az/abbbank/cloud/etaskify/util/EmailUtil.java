package az.abbbank.cloud.etaskify.util;

import az.abbbank.cloud.etaskify.entity.Employee;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailUtil {
    private JavaMailSender javaMailSender;
    private final String FROM = "noreply@abb-bank.az";

    public void notifyEmployeesByEmail(long taskId, List<Employee> employees){
        SimpleMailMessage msg;
        msg = new SimpleMailMessage();
        msg.setFrom(FROM);
        for(Employee employee : employees){
            msg.setTo(employee.getEmail());
        }
        msg.setSubject("New Task #" + taskId);
        msg.setText("Task #" + taskId + " Has Been Assigned To You");

        javaMailSender.send(msg);
    }
}
