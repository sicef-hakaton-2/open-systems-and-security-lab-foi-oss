<?php

namespace AppBundle\DataFixtures\ORM;

use AppBundle\Entity\Refugee;
use Zantolov\AppBundle\DataFixtures\ORM\AbstractDbFixture;

use Zantolov\AppBundle\Entity\User;

class LoadRefugeesData extends AbstractDbFixture
{

    public function load(\Doctrine\Common\Persistence\ObjectManager $manager)
    {
        $faker = \Faker\Factory::create();
        $userManager = $this->container->get('fos_user.user_manager');

        for ($i = 1; $i <= 50; $i++) {
            /** @var User $user */
            $user = $userManager->createUser();
            $user->setUsername('refugee' . $i);
            $user->setEmail('refugee' . $i . '@mailinator.com');
            $user->setPlainPassword('123456');
            $user->setEnabled(true);
            $user->setRoles(array('ROLE_USER', 'ROLE_REFUGEE'));
            $this->addReference('refugee' . $i, $user);
            $userManager->updateUser($user, true);

            $refugee = new Refugee();
            $refugee->setUser($user);
            $refugee->setIsMissing(rand(0,1));
            $refugee->setActive(true);
            $refugee->setBirthDate($faker->dateTime);
            $refugee->setFirstName($faker->firstName);
            $refugee->setLastName($faker->lastName);
            $refugee->setCountry($faker->country);
            $refugee->setExternalId($faker->randomNumber(5));

            $refugee->getImages()->add($this->getReference('image' . rand(1, 19)));

            $manager->persist($refugee);
            $this->setReference('refugee' . $i, $refugee);

        }

        $manager->flush();
    }

    public function getOrder()
    {
        return 2;
    }

}
