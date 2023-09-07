package org.example.Service;

import org.example.model.Computer;
import org.example.model.User;

import java.util.List;

public interface IComputerService {
    List<Computer> getAllComputer();
    void deleteComputer(long id);
    void createComputer(Computer computer);
    Computer findComputer(long id);
    Computer findComputerByName(String computerName);
    void updateComputerStatus(String name, long idStatus);
    void updateComputer(long id, Computer computer);
    void updateComputer(String name, Computer computer);
    void updateComputerName(String name,String newname);
    void updateAllComputerPricePerHour(long price);
    boolean checkComputerStatus(String computerName);

    void calculateBalanceOfEachUser();
void updateComputerStatusFromInUsetoReady(Computer computer);
}
