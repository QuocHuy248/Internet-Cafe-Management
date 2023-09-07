package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Utils.DateUtils;
import org.example.Utils.TimeUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory implements IParseModel<TransactionHistory>{
    private long id;
    private String username;
    private LocalDate dateDeposit;
    private long amountDeposit;
    @Override
    public TransactionHistory parse(String line) {
        TransactionHistory p=null;
        String[] items = line.split(",");
        try{
         p = new TransactionHistory(Long.parseLong(items[0]),items[1], DateUtils.parseDate(items[2]),Long.parseLong(items[3]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public String toString(){
        return String.format("%s,%s,%s,%s",this.id,this.username,DateUtils.formatDate(this.dateDeposit),this.amountDeposit);
    }

}
