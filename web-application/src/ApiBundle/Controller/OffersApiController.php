<?php

namespace ApiBundle\Controller;


use AppBundle\Entity\Offer;
use AppBundle\Service\RoleService;
use Symfony\Component\HttpFoundation\JsonResponse;
use Zantolov\AppBundle\Controller\API\ApiController;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Zantolov\AppBundle\Entity\User;


/**
 *
 * @Route("/api/offers")
 */
class OffersApiController extends ApiController
{

    /**
     *
     * @Route("/", name="offers.index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $items = $this->getDoctrine()->getManager()->getRepository('AppBundle:Offer')->findBy([
            'refugee' => null,
            'active'  => true
        ]);
        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $items
        ]);
    }

    /**
     * @Route("/{id}/accept", name="offers.accept")
     * @Method("POST")
     */
    public function acceptOffer($id)
    {
        $em = $this->getDoctrine()->getManager();
        $user = $this->getCurrentUser();

        if (empty($user) || !($user instanceof User)) {
            return $this->createResponse([
                self::KEY_STATUS  => self::STATUS_ERROR,
                self::KEY_MESSAGE => 'Please log in'
            ]);
        }

        /** @var Offer $offer */
        $offer = $em->getRepository('AppBundle:Offer')->find($id);

        if (empty($offer) || $offer->getActive() !== true) {
            return $this->createResponse([
                self::KEY_STATUS  => self::STATUS_ERROR,
                self::KEY_MESSAGE => 'Invalid Offer'
            ]);
        }

        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($user);

        // Offer can be accepted only by refugee
        if (!isset($profiles['profile_refugee']) || empty($profiles['profile_refugee'])) {
            return $this->createResponse([
                self::KEY_STATUS  => self::STATUS_ERROR,
                self::KEY_MESSAGE => 'User is not refugee'
            ]);
        }

        $offer->setRefugee($profiles['profile_refugee']);
        $offer->setActive(false);
        $em->flush();

        return $this->createResponse([
            self::KEY_STATUS  => self::STATUS_OK,
            self::KEY_MESSAGE => 'Accepted'
        ]);
    }


    /**
     * @Route("/{id}", name="offers.details")
     * @Method("GET")
     */
    public function detailsAction($id)
    {
        $ref = $this->getDoctrine()->getManager()->getRepository('AppBundle:Offer')->findOneBy(['id' => $id]);

        if (empty($ref) || $ref->getActive() !== true) {
            throw $this->createNotFoundException('Offer not found with id ' . $id);
        }

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $ref
        ]);
    }
}