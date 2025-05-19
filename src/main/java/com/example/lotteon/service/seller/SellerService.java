package com.example.lotteon.service.seller;

import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.seller.Seller;
import com.example.lotteon.entity.seller.SellerId;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.exception.EntityAlreadyExistsException;
import com.example.lotteon.repository.jpa.seller.SellerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerService {

  private final SellerRepository repo;
  private final ModelMapper mapper;
  private final PasswordEncoder encoder;

  private void encryptPassword(SellerDTO seller) {
    UserDTO user = seller.getSellerId().getUser();
    String encrypted = encoder.encode(user.getPassword());
    user.setPassword(encrypted);
  }

  private Seller toEntity(SellerDTO dto) {
    User user = mapper.map(dto.getSellerId().getUser(), User.class);
    SellerId compositeKey = SellerId.builder()
        .businessNumber(dto.getSellerId().getBusinessNumber())
        .user(user)
        .build();
    return Seller.builder()
        .sellerId(compositeKey)
        .ceo(dto.getCeo())
        .companyName(dto.getCompanyName())
        .sellerNumber(dto.getSellerNumber())
        .fax(dto.getFax())
        .status(dto.getStatus())
        .build();
  }

  public SellerDTO getSellerBy(String businessNumber) {
    Seller seller = repo.findByBusinessNumber(businessNumber);
    return mapper.map(seller, SellerDTO.class);
  }

  public Page<Seller> getAllPages(Pageable pageable) {
    return repo.findAll(pageable);
  }

  public Page<Seller> getAllByCeo(String ceo, Pageable pageable) {
    return repo.findAllByCeo(ceo, pageable);
  }

  public Page<Seller> getAllByCompanyName(String companyName, Pageable pageable) {
    return repo.findAllByCompanyName(companyName, pageable);
  }

  public Page<Seller> getAllByContact(String contact, Pageable pageable) {
    return repo.findAllByContact(contact, pageable);
  }

  public Page<Seller> getAllByBusinessNumber(String businessNumber, Pageable pageable) {
    return repo.findAllByBusinessNumber(businessNumber, pageable);
  }

  public void save(SellerDTO sellerDTO) throws EntityAlreadyExistsException {
    String businessNumber = sellerDTO.getSellerId().getBusinessNumber();
    if (repo.existsByBusinessNumber(businessNumber)) {
      throw new EntityAlreadyExistsException("중복된 사업자등록번호입니다.");
    }

    sellerDTO.setStatus(SellerDTO.STATUS_READY);

    Seller seller = toEntity(sellerDTO);
    repo.save(seller);
  }

  public void deleteByBusinessNumbers(List<String> businessNumbers) {
    repo.deleteByBusinessNumbers(businessNumbers);
  }

  public void updateStatus(String businessNumber, String newStatus) {
    repo.updateStatus(businessNumber, newStatus);
  }
}
