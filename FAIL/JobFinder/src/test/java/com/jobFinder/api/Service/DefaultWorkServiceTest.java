package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.Address;
import com.jobFinder.api.Pojo.Entity.User;
import com.jobFinder.api.Pojo.Entity.Work;
import com.jobFinder.api.Pojo.Input.WorkInput;
import com.jobFinder.api.Repository.AddressRepository;
import com.jobFinder.api.Repository.UserRepository;
import com.jobFinder.api.Repository.WorkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DefaultWorkServiceTest {

    @Mock
    private WorkRepository workRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultWorkService workService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveWork_ShouldSaveWorkWithAddressAndUser() {
        WorkInput workInput = new WorkInput();
        workInput.setTitle("Test Title");
        workInput.setDescription("Test Description");
        Address address = new Address();
        User user = new User();
        int userId = 123;


        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(userRepository.findUserById(userId)).thenReturn(user);
        when(workRepository.save(any(Work.class))).thenReturn(new Work());


        workService.saveWork(workInput, userId);

        verify(userRepository, times(1)).findUserById(userId);
        verify(workRepository, times(1)).save(any(Work.class));
    }

    @Test
    public void updateWork_ShouldUpdateWorkWithNewTitle() {
        WorkInput workInput = new WorkInput();
        workInput.setTitle("Updated Title");
        int workId = 456;

        Work existingWork = new Work();
        existingWork.setId(workId);
        existingWork.setTitle("Old Title");

        when(workRepository.findById(workId)).thenReturn(existingWork);
        when(workRepository.save(existingWork)).thenReturn(existingWork);

        Work updatedWork = workService.updateWork(workInput, workId);

        verify(workRepository, times(1)).findById(workId);
        verify(workRepository, times(1)).save(existingWork);
        verifyNoInteractions(addressRepository);

        assertEquals(workInput.getTitle(), updatedWork.getTitle());
        assertNull(updatedWork.getAddress());
    }

    @Test
    public void saveWork_ShouldHandleNullAddress() {
        WorkInput workInput = new WorkInput();
        workInput.setTitle("Test Title");
        workInput.setDescription("Test Description");
        Address address = new Address();
        User user = new User();
        int userId = 123;

        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(userRepository.findUserById(userId)).thenReturn(user);
        when(workRepository.save(any(Work.class))).thenReturn(new Work());

        workService.saveWork(workInput, userId);
        addressRepository.save(address);


        verify(addressRepository, times(1)).save(any(Address.class));
        verify(userRepository, times(1)).findUserById(userId);
        verify(workRepository, times(1)).save(any(Work.class));
        // Add assertions to verify that the appropriate handling of null address is performed
        // For example:
        // Assert that an error message is logged or an exception is thrown
        // assertNotNull(workService.getErrorMessage());
        // OR
        // assertThrows(NullPointerException.class, () -> workService.saveWork(workInput, userId));
    }
    @Test
    public void saveWork_ShouldHandleEmptyTitleAndDescription() {
        WorkInput workInput = new WorkInput();
        workInput.setTitle("");
        workInput.setDescription("");
        Address address = new Address();
        User user = new User();
        int userId = 123;

        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(userRepository.findUserById(userId)).thenReturn(user);
        when(workRepository.save(any(Work.class))).thenReturn(new Work());

        Work savedWork = workService.saveWork(workInput, userId);

        verify(userRepository, times(1)).findUserById(userId);
        verify(workRepository, times(1)).save(any(Work.class));

        assertNull( savedWork.getTitle());
        assertNull( savedWork.getDescription());

    }

    @Test
    public void updateWork_ShouldUpdateWorkWithNullAddress() {
        WorkInput workInput = new WorkInput();
        workInput.setTitle("Updated Title");
        int workId = 456;

        Work existingWork = new Work();
        existingWork.setId(workId);
        existingWork.setTitle("Old Title");
        existingWork.setAddress(null); // Set address to null

        when(workRepository.findById(workId)).thenReturn(existingWork);
        when(workRepository.save(existingWork)).thenReturn(existingWork);

        Work updatedWork = workService.updateWork(workInput, workId);

        verify(workRepository, times(1)).findById(workId);
        verify(workRepository, times(1)).save(existingWork);
        verifyNoInteractions(addressRepository);

        assertEquals(workInput.getTitle(), updatedWork.getTitle());
        assertNull(updatedWork.getAddress());
    }



}
