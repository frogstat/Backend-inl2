package se.yrgo.services.calls;

import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.Collection;

public class CallHandlingServiceImpl implements CallHandlingService {

    private DiaryManagementService diaryManagementService;
    private CustomerManagementService customerManagementService;

    public CallHandlingServiceImpl(DiaryManagementService diaryManagementService, CustomerManagementService customerManagementService) {
        this.diaryManagementService = diaryManagementService;
        this.customerManagementService = customerManagementService;
    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        customerManagementService.recordCall(customerId, newCall);
        for (Action action : actions) {
            diaryManagementService.recordAction(action);
        }
    }
}
