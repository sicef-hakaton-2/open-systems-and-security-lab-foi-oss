<?php

namespace AppBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\CallbackTransformer;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolverInterface;
use Zantolov\AppBundle\Form\Type\DatetimePickerType;
use Zantolov\MediaBundle\Form\EventSubscriber\ImagesChooserFieldAdderSubscriber;

class RefugeeType extends AbstractType
{
    /**
     * @param FormBuilderInterface $builder
     * @param array $options
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $imagesSubscriber = new ImagesChooserFieldAdderSubscriber('images');
        $builder->addEventSubscriber($imagesSubscriber);

        $builder
            ->add('firstName')
            ->add('lastName')
            ->add('externalId')
            ->add('birthDate', new DatetimePickerType())
            ->add('country')
            ->add('active')
            ->add('images')
            ->add('isMissing');

        $builder->get('birthDate')->addModelTransformer(new CallbackTransformer(
            function ($dateTime) {
                if (empty($dateTime)) {
                    $dateTime = new \DateTime();
                }

                if ($dateTime instanceof \Datetime) {
                    return $dateTime->format('d.m.Y. H:i');
                }
            },
            function ($datetimeText) {
                return \DateTime::createFromFormat('d.m.Y. H:i', $datetimeText);
            }
        ));

    }

    /**
     * @param OptionsResolverInterface $resolver
     */
    public function setDefaultOptions(OptionsResolverInterface $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'AppBundle\Entity\Refugee'
        ));
    }

    /**
     * @return string
     */
    public function getName()
    {
        return 'appbundle_refugee';
    }
}
