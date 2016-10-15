<?php

namespace ApiBundle\Controller;


use AppBundle\Entity\Citizen;
use AppBundle\Entity\Refugee;
use AppBundle\Service\RoleService;
use Symfony\Component\HttpFoundation\File\Exception\AccessDeniedException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpKernel\Exception\AccessDeniedHttpException;
use Zantolov\AppBundle\Controller\API\ApiController;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Template;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Zantolov\AppBundle\Entity\User;


/**
 *
 * @Route("/api/user")
 */
class ProfileApiController extends ApiController
{

    /**
     *
     * @Route("/profile", name="profile.index")
     * @Method("GET")
     */
    public function indexAction()
    {
        /** @var User $user */
        $user = $this->getCurrentUser();

        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($user);

        foreach ($profiles as $profileName => $profile) {
            //https://chart.googleapis.com/chart?cht=qr&chl=123456789&chs=200x200
            if (!empty($profile)) {
                $profile = $profile->jsonSerialize();
                $profile['url'] = $this->generateUrl('site.profile.check', ['id' => $profile['id']]);
                $profile['qr'] = 'https://chart.googleapis.com/chart?cht=qr&chs=400x400&chl=' . $profile['url'];
            }

            $data[$profileName] = $profile;
        }
        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $profiles
        ]);
    }

    /**
     *
     * @Route("/profile/offers", name="profile.offers")
     * @Method("GET")
     */
    public function offersAction()
    {
        /** @var User $user */
        $user = $this->getCurrentUser();
        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($user);

        if (isset($profiles['profile_refugee']) && $profiles['profile_refugee'] instanceof Refugee) {
            // If I am refugee, I want to see offers I accepted

            $items = $this->getDoctrine()->getManager()->getRepository('AppBundle:Offer')->findBy([
                'refugee' => $profiles['profile_refugee']->getId(),
            ]);

            $return = [];
            foreach ($items as &$item) {
                $returnItem = $item->jsonSerialize();
                $returnItem['contact'] =  $item->getCitizen()->jsonSerialize();
                $returnItem['contact']['email'] = $item->getCitizen()->getUser()->getEmail();
                $return[] = $returnItem;
            }


        } elseif (isset($profiles['profile_citizen']) && $profiles['profile_citizen'] instanceof Citizen) {
            // If I am citizen, I want to see offers I offered

            $items = $this->getDoctrine()->getManager()->getRepository('AppBundle:Offer')->findBy([
                'citizen' => $profiles['profile_citizen']->getId(),
            ]);

            $return = [];
            foreach ($items as &$item) {
                $returnItem = $item->jsonSerialize();
                $returnItem['contact'] = $item->getRefugee()->jsonSerialize();
                $returnItem['contact']['email'] = $item->getRefugee()->getUser()->getEmail();
                $return[] = $returnItem;
            }

        }

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $return
        ]);
    }

    /**
     *
     * @Route("/profile/requests", name="profile.requests")
     * @Method("GET")
     */
    public function requestsAction()
    {
        /** @var User $user */
        $user = $this->getCurrentUser();
        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($user);

        if (isset($profiles['profile_refugee']) && $profiles['profile_refugee'] instanceof Refugee) {
            // If I am refugee, I want to see requests I submitted

            $items = $this->getDoctrine()->getManager()->getRepository('AppBundle:Request')->findBy([
                'refugee' => $profiles['profile_refugee']->getId(),
            ]);

            $return = [];
            foreach ($items as &$item) {
                $returnItem = $item->jsonSerialize();
                $returnItem['contact'] = $item->getCitizen()->jsonSerialize();
                $returnItem['contact']['email'] = $item->getCitizen()->getUser()->getEmail();
                $return[] = $returnItem;
            }


        } elseif (isset($profiles['profile_citizen']) && $profiles['profile_citizen'] instanceof Citizen) {
            // If I am citizen, I want to see requests I accepted

            $items = $this->getDoctrine()->getManager()->getRepository('AppBundle:Request')->findBy([
                'citizen' => $profiles['profile_citizen']->getId(),
            ]);

            $return = [];
            foreach ($items as &$item) {
                $returnItem = $item->jsonSerialize();
                $returnItem['contact'] = $item->getRefugee()->jsonSerialize();
                $returnItem['contact']['email'] = $item->getRefugee()->getUser()->getEmail();
                $return[] = $returnItem;
            }

        }

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $return,
        ]);
    }


}