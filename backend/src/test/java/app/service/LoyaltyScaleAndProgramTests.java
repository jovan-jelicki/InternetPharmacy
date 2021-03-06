package app.service;

import app.model.pharmacy.LoyaltyCategory;
import app.model.pharmacy.LoyaltyProgram;
import app.model.pharmacy.LoyaltyScale;
import app.repository.LoyaltyProgramRepository;
import app.repository.LoyaltyScaleRepository;
import app.service.impl.LoyaltyProgramServiceImpl;
import app.service.impl.LoyaltyScaleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoyaltyScaleAndProgramTests {

    @InjectMocks
    private LoyaltyScaleServiceImpl loyaltyScaleService;

    @InjectMocks
    private LoyaltyProgramServiceImpl loyaltyProgramService;

    @Mock
    private LoyaltyScaleRepository loyaltyScaleRepository;

    @Mock
    private LoyaltyProgramRepository loyaltyProgramRepository;

    @Test
    public void editLoyaltyProgramTest(){
        LoyaltyProgram  loyaltyProgram=new LoyaltyProgram();
        loyaltyProgram.setId(0L);
        loyaltyProgram.setConsultingPoints(5);
        loyaltyProgram.setAppointmentPoints(10);

        LoyaltyProgram  loyaltyProgram1=new LoyaltyProgram();
        loyaltyProgram1.setId(1L);
        loyaltyProgram1.setConsultingPoints(7);
        loyaltyProgram1.setAppointmentPoints(13);

        ArrayList<LoyaltyProgram> loyaltyPrograms=new ArrayList<>();
        loyaltyPrograms.add(loyaltyProgram);
        loyaltyPrograms.add(loyaltyProgram1);

        when(loyaltyProgramService.read()).thenReturn(loyaltyPrograms);
        when(loyaltyProgramService.save(loyaltyProgram1)).thenReturn(loyaltyProgram1);
        assertThat(loyaltyProgramService.saveProgram(loyaltyProgram1), is(equalTo(true)));

    }


    @Test
    public void editLoyaltyScaleTest(){
        LoyaltyScale loyaltyScale=new LoyaltyScale();
        loyaltyScale.setId(0L);
        loyaltyScale.setCategory(LoyaltyCategory.regular);
        loyaltyScale.setMinPoints(10);
        loyaltyScale.setMaxPoints(20);
        loyaltyScale.setDiscount(30);

        LoyaltyScale loyaltyScale1=new LoyaltyScale();
        loyaltyScale1.setId(1L);
        loyaltyScale1.setCategory(LoyaltyCategory.silver);
        loyaltyScale1.setMinPoints(21);
        loyaltyScale1.setMaxPoints(31);
        loyaltyScale1.setDiscount(40);

        LoyaltyScale loyaltyScale2=new LoyaltyScale();
        loyaltyScale2.setId(2L);
        loyaltyScale2.setCategory(LoyaltyCategory.gold);
        loyaltyScale2.setMinPoints(32);
        loyaltyScale2.setMaxPoints(42);
        loyaltyScale2.setDiscount(50);

        LoyaltyScale loyaltyScaleEdit=new LoyaltyScale();
        loyaltyScaleEdit.setId(2L);
        loyaltyScaleEdit.setCategory(LoyaltyCategory.gold);
        loyaltyScaleEdit.setMinPoints(45);
        loyaltyScaleEdit.setMaxPoints(55);
        loyaltyScaleEdit.setDiscount(33);


        ArrayList<LoyaltyScale> loyaltyScales=new ArrayList<>();
        loyaltyScales.add(loyaltyScale);
        loyaltyScales.add(loyaltyScale1);
        loyaltyScales.add(loyaltyScale2);

        when(loyaltyScaleService.read()).thenReturn(loyaltyScales);
        when(loyaltyScaleService.save(loyaltyScaleEdit)).thenReturn(loyaltyScaleEdit);
        assertThat(loyaltyScaleService.editLoyaltyScale(loyaltyScaleEdit), is(equalTo(true)));

    }


}
