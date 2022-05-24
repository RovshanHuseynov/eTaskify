package az.abbbank.cloud.etaskify.util;

import az.abbbank.cloud.etaskify.entity.Employee;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class EmailUtil {
    public JavaMailSender javaMailSender;
    public String from = "noreply@gmail.com";

    public void notifyEmployeesByEmail(long taskId, List<Employee> employees){
        SimpleMailMessage msg;
        for(Employee employee : employees){
            msg = new SimpleMailMessage();
            msg.setTo(from, employee.getEmail());

            msg.setSubject("New Task Assigned");
            msg.setText("You have a new task which id: " + taskId);

            javaMailSender.send(msg);
        }
    }
}
