package org.example.Service;

import org.example.Utils.DateUtils;
import org.example.Utils.FileUtils;
import org.example.Utils.TimeUtils;
import org.example.model.Computer;
import org.example.model.EStatusComputer;
import org.example.model.Product;
import org.example.model.User;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class ComputerService implements IComputerService {
    private final String fileComputer = "./data/Computers.txt";

    @Override
    public List<Computer> getAllComputer() {
        return FileUtils.readData(fileComputer, Computer.class);
    }

    @Override
    public void deleteComputer(long id) {
        List<Computer> computers = getAllComputer();
        Computer computer = null;
        for (Computer p : computers) {
            if (p.getId() == id) {
                computer = p;
            }
        }
        computers.remove(computer);
        FileUtils.writeData(fileComputer, computers);

    }

    @Override
    public void createComputer(Computer computer) {
        List<Computer> computers = getAllComputer();
        computer.setId(computers.size() + 1);
        computers.add(computer);
        FileUtils.writeData(fileComputer, computers);
    }

    @Override
    public Computer findComputer(long id) {
        List<Computer> computers = getAllComputer();
        Computer p = computers.stream().filter(computer -> computer.getId() == id).findFirst().orElseThrow(null);
        return p;
    }

    @Override
    public void updateComputerStatus(String name, long idStatus) {
        List<Computer> computers = getAllComputer();
        computers.stream().filter(computer -> computer.getName().equals(name)).findFirst().orElseThrow(null).setStatusComputer(EStatusComputer.findById(idStatus));
        FileUtils.writeData(fileComputer, computers);
    }

    @Override
    public void updateAllComputerPricePerHour(long price) {
        Computer.setPrice(price);
    }

    @Override
    public void updateComputer(long id, Computer computer) {
        List<Computer> computers = getAllComputer();
        for (Computer p : computers) {
            if (p.getId() == id) {
                p.setName(computer.getName());
                p.setStatusComputer(computer.getStatusComputer());
                p.setUsername(computer.getUsername());
                p.setStartUsing(computer.getStartUsing());

            }
        }
        FileUtils.writeData(fileComputer, computers);
    }

    @Override
    public boolean checkComputerStatus(String computerName) {
        List<Computer> computers = getAllComputer();
        boolean check = false;
        Computer computer = computers.stream().filter(computer1 -> computer1.getName().equals(computerName)).findFirst().orElseThrow(null);
        if (computer.getStatusComputer().equals(EStatusComputer.InUse) || computer.getStatusComputer().equals(EStatusComputer.UnderMaintenance)) {
            check = false;
        } else if (computer.getStatusComputer().equals(EStatusComputer.Ready)) {
            check = true;
        }
        return check;
    }

    @Override
    public Computer findComputerByName(String computerName) {
        List<Computer> computers = getAllComputer();
        Computer p = computers.stream().filter(computer -> computer.getName().equals(computerName)).findFirst().orElseThrow(null);
        return p;
    }


    @Override
    public void updateComputerName(String name, String newname) {
        List<Computer> computers = getAllComputer();
         computers.stream().filter(computer -> computer.getName().equals(name)).findFirst().get().setName(newname);
         computers.stream().filter(computer -> computer.getName().equals(name)).findFirst().get().setUsername(newname);
         FileUtils.writeData(fileComputer,computers);

    }

    @Override
    public void calculateBalanceOfEachUser() {

    }

    @Override
    public void updateComputerStatusFromInUsetoReady(Computer computer) {
        List<Computer> computers = getAllComputer();
        computers.stream().filter(computer2 -> computer2.getName().equals(computer.getName())).findFirst().get().setStatusComputer(EStatusComputer.Ready);
        computers.stream().filter(computer2 -> computer2.getName().equals(computer.getName())).findFirst().get().setUsername(computer.getName());
        computers.stream().filter(computer2 -> computer2.getName().equals(computer.getName())).findFirst().get().setStartUsing(TimeUtils.parseTime("00:00"));
        FileUtils.writeData(fileComputer, computers);
    }

    public static void main(String[] args) {
        String computername = "MAY01";
        ComputerService computerService = new ComputerService();
        computerService.findComputerByName(computername);
    }

    @Override
    public void updateComputer(String name, Computer computer) {
        List<Computer> computers = getAllComputer();
        for (Computer p : computers) {
            if (p.getName().equals(name)) {
                p.setId(computer.getId());
                p.setStatusComputer(computer.getStatusComputer());
                p.setUsername(computer.getUsername());
                p.setStartUsing(computer.getStartUsing());
            }
        }
        FileUtils.writeData(fileComputer, computers);
    }
}