package ru.kutnyashenko.retailservice.testtaskmiddle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kutnyashenko.retailservice.testtaskmiddle.entity.*;
import ru.kutnyashenko.retailservice.testtaskmiddle.repository.BookingPositionRepository;
import ru.kutnyashenko.retailservice.testtaskmiddle.repository.BookingRepository;
import ru.kutnyashenko.retailservice.testtaskmiddle.repository.LabelRepository;
import ru.kutnyashenko.retailservice.testtaskmiddle.repository.WarehouseRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final LabelRepository labelRepository;
    private final BookingRepository bookingRepository;
    private final BookingPositionRepository bookingPositionRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, LabelRepository labelRepository, BookingRepository bookingRepository, BookingPositionRepository bookingPositionRepository) {
        this.warehouseRepository = warehouseRepository;
        this.labelRepository = labelRepository;
        this.bookingRepository = bookingRepository;
        this.bookingPositionRepository = bookingPositionRepository;
    }

    @Transactional()
    public Result getResult(Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        List<BookingPosition> positions = booking.getPositions();
        positions.forEach(bookingPosition -> bookingPosition.setOwner(savedBooking));
        bookingPositionRepository.saveAll(positions);

        Set<String> labelsBooking = new HashSet<>();
        Map<String, Integer> needLabels = new HashMap<>();

        for (BookingPosition position : positions) {
            labelsBooking.add(position.getLabel());
            needLabels.put(position.getLabel(), position.getCnt());
        }

        List<ResultPosition> resultPositions = new ArrayList<>();
        List<Warehouse> warehouses = warehouseRepository.findAll();
        Map<String, Integer> maxCountLabelInWarehouse = findMaxCountLabelInWarehouse(labelsBooking);


        for (Warehouse warehouse : warehouses) {
            if (warehouse.getLabels().size() == 1) {
                if (addThenSizeEqualOne(needLabels, resultPositions, maxCountLabelInWarehouse, warehouse)) break;
            } else if (warehouse.getLabels().size() > 1){
                if (addThenSizeMoreOne(needLabels, resultPositions, maxCountLabelInWarehouse, warehouse)) break;
            }
        }

        return needLabels.size() > 0 ? new UnsuccessfulResult():
                new SuccessfulResult(resultPositions);
    }

    private boolean addThenSizeMoreOne(Map<String, Integer> needLabels, List<ResultPosition> resultPositions,
                                              Map<String, Integer> maxCountLabelInWarehouse, Warehouse warehouse) {
        boolean result = false;
        List<String> labelsInWarehouse = warehouse.getLabels().stream().map(Label::getLabel).toList();
        String currentLabel = warehouse.getLabels().get(0).getLabel();
        int currentCount = warehouse.getQuantity();
        currentLabel = findCurrentLabel(maxCountLabelInWarehouse, labelsInWarehouse, currentLabel);
        boolean containsKey = needLabels.containsKey(currentLabel);
        if (!containsKey){
            return result;
        }
        addToResultPosition(needLabels, resultPositions, maxCountLabelInWarehouse, warehouse, currentLabel, currentCount);
        if (needLabels.isEmpty()){
            result = true;
        }
        return result;
    }

    private boolean addThenSizeEqualOne(Map<String, Integer> needLabels, List<ResultPosition> resultPositions,
                                               Map<String, Integer> maxCountLabelInWarehouse, Warehouse warehouse) {
        boolean result = false;
        String currentLabel = warehouse.getLabels().get(0).getLabel();
        int currentCount = warehouse.getQuantity();
        boolean containsKey = needLabels.containsKey(currentLabel);
        if (!containsKey){
            return result;
        }
        addToResultPosition(needLabels, resultPositions, maxCountLabelInWarehouse, warehouse, currentLabel, currentCount);
        if (needLabels.isEmpty()){
            result = true;
        }
        return result;
    }

    private static void addToResultPosition(Map<String, Integer> needLabels, List<ResultPosition> resultPositions,
                                            Map<String, Integer> maxCountLabelInWarehouse, Warehouse warehouse,
                                            String currentLabel, int currentCount) {
        if (warehouse.getQuantity() < needLabels.get(currentLabel)) {
            needLabels.put(currentLabel, needLabels.get(currentLabel) - warehouse.getQuantity());
            maxCountLabelInWarehouse.put(currentLabel, maxCountLabelInWarehouse.get(currentLabel) - warehouse.getQuantity());
        } else {
            currentCount = needLabels.get(currentLabel);
            needLabels.remove(currentLabel);
            maxCountLabelInWarehouse.remove(currentLabel);
        }
        resultPositions.add(new ResultPosition(warehouse.getId(), currentCount));
    }

    private static String findCurrentLabel(Map<String, Integer> maxCountLabelInWarehouse,
                                           List<String> labelsInWarehouse, String currentLabel) {
        int min = Integer.MAX_VALUE;
        for (String s : labelsInWarehouse) {
            if (maxCountLabelInWarehouse.containsKey(s) && maxCountLabelInWarehouse.get(s) < min){
                min = maxCountLabelInWarehouse.get(s);
                currentLabel = s;
            }
        }
        return currentLabel;
    }

    private Map<String, Integer> findMaxCountLabelInWarehouse(Set<String> labelsBooking) {
        List<Label> labels = labelRepository.findByLabelIn(labelsBooking);
        Map<String, Integer> maxCount = new HashMap<>();
        for (Label label : labels) {
            if (maxCount.containsKey(label.getLabel())) {
                maxCount.put(label.getLabel(), maxCount.get(label.getLabel()) + label.getOwner().getQuantity());
            } else {
                maxCount.put(label.getLabel(), label.getOwner().getQuantity());
            }
        }
        return maxCount;
    }
}
