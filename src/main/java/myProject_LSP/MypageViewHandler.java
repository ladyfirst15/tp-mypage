package myProject_LSP;

import myProject_LSP.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setRestaurantId(ordered.getRestaurantId());
                mypage.setRestaurantMenuId(ordered.getRestaurantMenuId());
                mypage.setCustomerId(ordered.getCustomerId());
                mypage.setQty(ordered.getQty());
                mypage.setOrderId(ordered.getId());
                mypage.setOrderStatus(ordered.getStatus());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenCooked_then_UPDATE_1(@Payload Cooked cooked) {
        System.out.println("44444444444444444444444444444");
        try {
            if (cooked.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(cooked.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setCookId(cooked.getId());
                    mypage.setCookStatus(cooked.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCancelled_then_UPDATE_2(@Payload OrderCancelled orderCancelled) {
        try {
            if (orderCancelled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(orderCancelled.getId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setOrderStatus(orderCancelled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenShipped_then_UPDATE_4(@Payload Shipped shipped) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22");
        try {
            if (shipped.isMe()) {
                // view 객체 조회
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@33");
                System.out.println(shipped.getOrderId());
                List<Mypage> mypageList = mypageRepository.findByOrderId(shipped.getOrderId());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@33");

                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setDeliveryStatus(shipped.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenCookQtyChecked_then_UPDATE_5(@Payload CookQtyChecked cookQtyChecked) {
        try {
            if (cookQtyChecked.isMe()) {
                // view 객체 조회

                List<Mypage> mypageList = mypageRepository.findByOrderId(cookQtyChecked.getOrderId());

                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함

                    mypage.setCookId(cookQtyChecked.getId());
                    mypage.setCookStatus(cookQtyChecked.getStatus());
                    mypage.setOrderStatus("ORDER : QTY OVER CANCELED");
                    mypage.setDeliveryStatus("DELIVERY : QTY OVER");


                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenShippedCancelled_then_UPDATE_6(@Payload ShippedCancelled shippedCancelled) {
        try {
            if (shippedCancelled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(shippedCancelled.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setDeliveryStatus(shippedCancelled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCookCancelled_then_UPDATE_3(@Payload CookCancelled cookCancelled) {
        try {
            if (cookCancelled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(cookCancelled.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setCookStatus(cookCancelled.getStatus());
                    mypage.setOrderStatus("ORDER : ORDER CANCELLED");
                    mypage.setDeliveryStatus("DELIVERY : ORDER CANCELLED");
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}